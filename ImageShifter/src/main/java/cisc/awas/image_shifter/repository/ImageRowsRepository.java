package cisc.awas.image_shifter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cisc.awas.image_shifter.entity.ImageRows;

public interface ImageRowsRepository extends JpaRepository<ImageRows, Long> {

	public ImageRows  findFirstByOrderByRowsAsc();
	
}
