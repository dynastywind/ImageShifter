package cisc.awas.image_shifter.service;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cisc.awas.image_shifter.entity.Image;
import cisc.awas.image_shifter.entity.ImageNew;
import cisc.awas.image_shifter.entity.Topic;
import cisc.awas.image_shifter.repository.ImageNewRepository;
import cisc.awas.image_shifter.repository.ImageRepository;
import cisc.awas.image_shifter.repository.TopicRepository;
import cisc.awas.image_shifter.util.ImageTransformer;

@Service(value = "imageShiftService")
@Transactional
public class ImageShiftServiceImpl implements ImageShiftService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ImageNewRepository imageNewRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Resource
	private Environment env;
	
	private static Long currentImageId = 0L;
	
	private static Logger logger = Logger.getLogger(ImageShiftServiceImpl.class);
	
	private static final String DEST_FILE_PREFIX = "dest.file.prefix";
	
	@Override
	public void shiftImage() {
		Image image = null;
		synchronized (this) {
			image  = imageRepository.findFirstByIdGreaterThan(currentImageId);
			currentImageId = image.getId();
		}
		try {
			Blob blob = image.getImageContent();
			byte[] bytes = blob.getBytes(1, (int)blob.length());
			String fileName = generateFileName(image);
			if(null == fileName) {
				logger.info("No Valid Topic.");
				imageRepository.delete(image);
				return;
			}
			if(null != imageNewRepository.findFirstByPostIdAndHashValue(image.getPostId(), image.getHashValue())) {
				logger.info("Content Duplicated");
				imageRepository.delete(image);
				return;
			}
			FileUtils.writeByteArrayToFile(new File(fileName), bytes);
			ImageNew imageNew = ImageTransformer.transform(image, fileName);
			imageNewRepository.saveAndFlush(imageNew);
			imageRepository.delete(image);
		} catch (DataAccessException e) {
			e.printStackTrace();
			imageRepository.delete(image);
		} catch (SQLException e) {
			e.printStackTrace();
			handleException(image.getId());
		} catch (IOException e) {
			e.printStackTrace();
			handleException(image.getId());
		}
	}
	
	@Override
	public long imageCounts() {
		return imageRepository.count();
	}
	
	private String generateFileName(Image image) {
		Topic topic = topicRepository.findOne(image.getTopicId());
		Calendar createDate = Calendar.getInstance();
		if(null != topic && null != topic.getCreateDate()) {
			createDate.setTime(topic.getCreateDate());
		} else {
			return null;
		}
		StringBuilder builder = new StringBuilder(env.getRequiredProperty(DEST_FILE_PREFIX));
		builder.append( topic.getForumId() + "\\" + createDate.get(Calendar.YEAR) + "\\" + (createDate.get(Calendar.MONTH) + 1) + "\\" + createDate.get(Calendar.DAY_OF_MONTH) + "\\" + image.getId() + "." + image.getContentType());
		return builder.toString();
	}
	
	private synchronized void handleException(long imageId) {
		currentImageId = imageId - 1;
	}

}
