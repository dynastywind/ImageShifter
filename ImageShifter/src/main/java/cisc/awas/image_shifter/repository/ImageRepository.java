package cisc.awas.image_shifter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cisc.awas.image_shifter.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	public Image findFirstByIdGreaterThan(long id);
	
}
