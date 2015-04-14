package cisc.awas.image_shifter.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Image {

	@Id
	@Column(name = "idimage")
	private long id;
	
	@Column(name = "topic_idtopic", nullable = false, unique = true)
	private long topicId;
	
	@Column(name = "post_idpost", nullable = false, unique = true)
	private long postId;
	
	@Column(name = "imagecontent")
	private Blob imageContent;
	
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
