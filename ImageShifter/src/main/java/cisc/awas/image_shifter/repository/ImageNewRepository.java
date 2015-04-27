package cisc.awas.image_shifter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cisc.awas.image_shifter.entity.ImageNew;

public interface ImageNewRepository extends JpaRepository<ImageNew, Long> {

	public ImageNew findFirstByPostIdAndHashValue(long postId, String hashValue);
	
}
