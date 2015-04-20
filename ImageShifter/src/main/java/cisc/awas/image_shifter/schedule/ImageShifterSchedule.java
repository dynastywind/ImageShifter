package cisc.awas.image_shifter.schedule;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cisc.awas.image_shifter.ImageShifter;

@Component("imageShifterSchedule")
public class ImageShifterSchedule {

	private static Logger logger = Logger.getLogger(ImageShifterSchedule.class);
	
	@Autowired
	ImageShifter imageShifter;
	
	public void shift() {
		logger.info("Starts to shift images...");
		imageShifter.startImageThreads(ImageShifter.ctx);
		logger.info("Image shifting ends.");
	}
	
}
