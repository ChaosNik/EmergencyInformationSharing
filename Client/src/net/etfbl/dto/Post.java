package net.etfbl.dto;

import java.util.Date;
import java.util.List;

public class Post {
	private Integer id;
	private String text;
	private User creator;
	private Date creationTime;
	private String location;
	private String video;
	private String link;
	private List<String> images;
	private Integer isEmergency;
	private List<Comment> commments;
	private List<String> categories;
	private Integer userId;
	private Boolean isFeed = false;

	public Post(Integer id, String text, User creator, Date creationTime, String location, String video, String link) {
		super();
		this.id = id;
		this.text = text;
		this.creator = creator;
		this.creationTime = creationTime;
		this.location = location;
		this.video = video;
		this.link = link;
	}

	public Post(String text, User creator, Date creationTime, String location, String video, Integer isEmergency,
			String link) {
		super();
		this.text = text;
		this.creator = creator;
		this.creationTime = creationTime;
		this.location = location;
		this.video = video;
		this.isEmergency = isEmergency;
		this.link = link;
	}

	public Post() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<Comment> getCommments() {
		return commments;
	}

	public void setCommments(List<Comment> commments) {
		this.commments = commments;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsEmergency() {
		return isEmergency;
	}

	public void setIsEmergency(Integer isEmergency) {
		this.isEmergency = isEmergency;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Boolean getIsFeed() {
		return isFeed;
	}

	public void setIsFeed(Boolean isFeed) {
		this.isFeed = isFeed;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", text=" + text + ", creator=" + creator + ", creationTime=" + creationTime
				+ ", location=" + location + ", video=" + video + ", images=" + images + "]";
	}
}
