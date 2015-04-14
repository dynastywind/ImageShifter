package cisc.awas.image_shifter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cisc.awas.image_shifter.service.ImageShiftService;

@Component("shifterThread")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShifterThread extends Thread {

	private static Logger logger = Logger.getLogger(ShifterThread.class);
	
	@Autowired
	private ImageShiftService imageShiftService;
	
	@Override
	public void run() {
		logger.info("Thread " + this.getId() + " starts to execute");
		imageShiftService.shiftImage();
		logger.info("Thread " + this.getId() + " finished");
	}
	
}
