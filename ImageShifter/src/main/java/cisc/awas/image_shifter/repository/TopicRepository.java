package cisc.awas.image_shifter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cisc.awas.image_shifter.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

}
