package cisc.awas.image_shifter;

import static org.junit.Assert.assertEquals;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.xml.bind.DatatypeConverter;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cisc.awas.image_shifter.entity.Image;
import cisc.awas.image_shifter.repository.ImageRepository;
import cisc.awas.image_shifter.service.ImageShiftService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ImageShifter.class)
public class JpaTest {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ImageShiftService imageShiftService;
	
	@Test
	@Ignore
	public void jpaTest() {
		imageShiftService.shiftImage();
	}
	
	@Test
	@Ignore
	public void hashTest() {
		Image image = imageRepository.findOne(1304409L);
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			Blob blob = image.getImageContent();
			byte[] bytes = digest.digest(blob.getBytes(1, (int)blob.length()));
			assertEquals(image.getHashValue(), DatatypeConverter.printHexBinary(bytes).toLowerCase());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void imageCountTest() {
		imageShiftService.imageCounts();
	}
	
	@Test
//	@Ignore
	public void triggerTest() {
		long originalCount = imageShiftService.imageCounts();
		imageRepository.delete(44149L);
		assertEquals(imageShiftService.imageCounts(), originalCount - 1);
	}
	
}
