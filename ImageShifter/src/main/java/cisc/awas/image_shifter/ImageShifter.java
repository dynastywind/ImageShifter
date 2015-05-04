package cisc.awas.image_shifter;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import cisc.awas.image_shifter.service.ImageShiftService;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "cisc.awas.image_shifter")
public class ImageShifter {
	
	@Autowired
	private ImageShiftService imageShiftService;
	
	@Resource
	private Environment env;
	
	private ThreadPoolExecutor threadPool;
	
	private String workMode;
	
	public static ConfigurableApplicationContext ctx;
	
	private static Logger logger = Logger.getLogger(ImageShifter.class);
	
	private static final String THREAD_NUM = "thread.num";

	private static final String RUNNING_PERIOD = "running.period";

	private static final String WORK_MODE = "work.mode";
	
	@PostConstruct
	private void initImageShifter() {
		int threadNum = Integer.parseInt(env.getRequiredProperty(THREAD_NUM));
		this.threadPool = new ThreadPoolExecutor(threadNum, threadNum, 1, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>());
		this.workMode = env.getRequiredProperty(WORK_MODE);
	}
	
	public void startImageThreads(ApplicationContext ctx) {
		long startTime = System.currentTimeMillis();
		while(imageShiftService.imageCounts() > 0 && withinAllowedTime(startTime)) {
			int diff = Integer.parseInt(env.getRequiredProperty(THREAD_NUM)) - threadPool.getActiveCount();
			for(int i = 0; i < diff; i++) {
				ShifterThread shifterThread = ctx.getBean(ShifterThread.class);
				threadPool.submit(shifterThread);
				logger.info("Thread " + shifterThread.getId() + " submitted");
				logger.info("Totally " + threadPool.getPoolSize() + " threads");
			}
		}
		logger.info("No image needs to be shifted.");
		threadPool.shutdown();
	}
	
	public boolean isScheduled() {
		logger.info("Work Mode " + workMode + " is ON");
		return workMode.equals(WorkMode.SCHEDULED.name());
	}
	
	private boolean withinAllowedTime(long startTime) {
		if(workMode.equals(WorkMode.SCHEDULED.name())) {
			return System.currentTimeMillis() - startTime <= Integer.parseInt(env.getRequiredProperty(RUNNING_PERIOD)) * 3600000L;
		}
		return true;
	}
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ImageShifter.class);
	    	app.setShowBanner(false);
	    	ctx = app.run(args);
	    	ImageShifter shifter = (ImageShifter)ctx.getBean(ImageShifter.class);
	    	if(shifter.isScheduled()) {
	    		new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"}, ctx);
	    	} else {	    		
	    		shifter.startImageThreads(ctx);
	    	}
	}

}