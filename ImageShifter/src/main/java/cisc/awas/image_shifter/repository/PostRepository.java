package cisc.awas.image_shifter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cisc.awas.image_shifter.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
