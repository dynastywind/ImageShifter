package cisc.awas.image_shifter.util;

import cisc.awas.image_shifter.entity.Image;
import cisc.awas.image_shifter.entity.ImageNew;

public class ImageTransformer {

	public static ImageNew transform(Image image, String fileName) {
		ImageNew imageNew = new ImageNew();
		imageNew.setId(image.getId());
		imageNew.setTopicId(image.getTopicId());
		imageNew.setPostId(image.getPostId());
		imageNew.setImageContent(null);
		imageNew.setImagePath(fileName);
		imageNew.setHashValue(image.getHashValue());
		imageNew.setUrl(image.getUrl());
		imageNew.setContentType(image.getContentType());
		return imageNew;
	}
	
}
