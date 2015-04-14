package cisc.awas.image_shifter.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "image2")
public class ImageNew {

	@Id
	@GeneratedValue(generator = "page_id")
	@GenericGenerator(name = "page_id", strategy = "assigned")
	@Column(name = "idimage")
	private long id;
	
	@Column(name = "topic_idtopic", nullable = false, unique = true)
	private long topicId;
	
	@Column(name = "post_idpost", nullable = false, unique = true)
	private long postId;
	
	@Column(name = "imageContent", nullable = false)
	private Blob imageContent;
	
	@Column(name = "imagepath", nullable = false, length = 1024)
	private String imagePath;
	
	@Column(name = "hashValue", length = 40)
	private String hashValue;
	
	@Column(name = "url", length = 1024)
	private String url;
	
	@Column(name = "contentType", length = 8)
	private String contentType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public Blob getImageContent() {
		return imageContent;
	}

	public void setImageContent(Blob imageContent) {
		this.imageContent = imageContent;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getHashValue() {
		return hashValue;
	}

	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
