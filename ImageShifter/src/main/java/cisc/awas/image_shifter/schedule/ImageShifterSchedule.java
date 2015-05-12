package cisc.awas.image_shifter.schedule;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cisc.awas.image_shifter.ImageShifter;

@Component("imageShifterSchedule")
@DisallowConcurrentExecution
public class ImageShifterSchedule {

	private static Logger logger = Logger.getLogger(ImageShifterSchedule.class);
	
	@Autowired
	ImageShifter imageShifter;
	
	public void shift() {
		logger.info("Scheduler: Starts to shift images...");
		imageShifter.startImageThreads(ImageShifter.ctx);
		logger.info("Scheduler: Image shifting ends.");
	}
	
}
