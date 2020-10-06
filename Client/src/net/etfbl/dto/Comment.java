package net.etfbl.dto;

import java.util.Date;

public class Comment {

	private String text;
	private String image;
	private Date time;
	private Integer postId;
	private Integer userId;

	public Comment(String text, String image, Integer postId) {
		super();
		this.text = text;
		this.image = image;
		this.postId = postId;
		this.time = new java.util.Date();
	}

	public Comment(String text, String image, Integer postId, Date time, Integer userId) {
		super();
		this.text = text;
		this.image = image;
		this.postId = postId;
		this.time = time;
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
