package cisc.awas.image_shifter.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post {
	
	@Id
	@Column(name = "idpost")
	private long id;
	
	@Column(name = "author_idauthor", nullable = false)
	private long authorId;
	
	@Column(name = "topic_idtopic", nullable = false)
	private long topicId;
	
	@Column(name = "tcreated", nullable = false)
	private Date createDate;
	
	@Column(name = "tfirstsurveillance", nullable = false)
	private Date surveillanceDate;
	
	@Column(name = "postorder", nullable = false)
	private int postOrder;
	
	@Column(name = "postretrievalkey", length = 45)
	private String retrivalKey;
	
	@Column(name = "url", length = 1024)
	private String url;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getSurveillanceDate() {
		return surveillanceDate;
	}

	public void setSurveillanceDate(Date surveillanceDate) {
		this.surveillanceDate = surveillanceDate;
	}

	public int getPostOrder() {
		return postOrder;
	}

	public void setPostOrder(int postOrder) {
		this.postOrder = postOrder;
	}

	public String getRetrivalKey() {
		return retrivalKey;
	}

	public void setRetrivalKey(String retrivalKey) {
		this.retrivalKey = retrivalKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
