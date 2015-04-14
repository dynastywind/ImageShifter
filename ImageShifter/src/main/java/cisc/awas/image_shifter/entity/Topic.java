package cisc.awas.image_shifter.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "topic")
public class Topic {

	@Id
	@Column(name = "idtopic")
	private long id;
	
	@Column(name = "author_idauthor")
	private long authorId;
	
	@Column(name = "forum_idforum", nullable = false)
	private int forumId;
	
	@Column(name = "category_idcategory", nullable = false)
	private long categoryId;
	
	@Column(name = "title", length = 255, nullable = false)
	private String title;
	
	@Column(name = "tcreated")
	private Date createDate;
	
	@Column(name = "tlastmodified")
	private Date lastModifiedDate;
	
	@Column(name = "tfirstsurveillance")
	private Date firstSurveillanceDate;
	
	@Column(name = "tlastsurveillance")
	private Date lastSurveillanceDate;
	
	@Column(name = "topicretrievalkey", length = 255, nullable = false)
	private String retrievalKey;
	
	@Column(name = "url", length = 512, nullable = false)
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

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getFirstSurveillanceDate() {
		return firstSurveillanceDate;
	}

	public void setFirstSurveillanceDate(Date firstSurveillanceDate) {
		this.firstSurveillanceDate = firstSurveillanceDate;
	}

	public Date getLastSurveillanceDate() {
		return lastSurveillanceDate;
	}

	public void setLastSurveillanceDate(Date lastSurveillanceDate) {
		this.lastSurveillanceDate = lastSurveillanceDate;
	}

	public String getRetrievalKey() {
		return retrievalKey;
	}

	public void setRetrievalKey(String retrievalKey) {
		this.retrievalKey = retrievalKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
