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

/**
 * Image Shifter.
 * @author Lyndon
 * @version 1.0
 */
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
	
	private int threadNum;
	
	public static ConfigurableApplicationContext ctx;
	
	private static Logger logger = Logger.getLogger(ImageShifter.class);
	
	private static final String THREAD_NUM = "thread.num";

	private static final String RUNNING_PERIOD = "running.period";

	private static final String WORK_MODE = "work.mode";
	
	@PostConstruct
	private void initImageShifter() {
		this.threadNum = Integer.parseInt(env.getRequiredProperty(THREAD_NUM));
		this.threadPool = new ThreadPoolExecutor(threadNum, threadNum, 1, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>());
		this.workMode = env.getRequiredProperty(WORK_MODE);
	}
	
	/**
	 * Start image shifter threads.
	 * @param ctx The given Spring context
	 */
	public void startImageThreads(ApplicationContext ctx) {
		if(this.threadPool.isTerminated()) {
			this.threadPool = new ThreadPoolExecutor(threadNum, threadNum, 1, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>());
		}
		long startTime = System.currentTimeMillis();
		while(imageShiftService.imageCounts() > threadPool.getActiveCount() && withinAllowedTime(startTime)) {
			if(threadPool.getActiveCount() > threadNum) {
				continue;
			}
			int diff = threadNum - threadPool.getActiveCount();
			for(int i = 0; i < diff; i++) {
				ShifterThread shifterThread = ctx.getBean(ShifterThread.class);
				threadPool.submit(shifterThread);
				logger.info("Thread " + shifterThread.getId() + " submitted");
				logger.info("Totally " + threadPool.getPoolSize() + " threads");
			}
		}
		imageShiftService.resetCurrentImageId();
		this.threadPool.shutdown();
		while(!this.threadPool.isTerminated()) {
		}
		logger.info("Image Shifting Task Ends.");
	}
	
	//Check the work mode
	public boolean isScheduled() {
		logger.info("Work Mode " + workMode + " is ON");
		return workMode.equals(WorkMode.SCHEDULED.name());
	}
	
	//Check whether the task is within the predefined executing period
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