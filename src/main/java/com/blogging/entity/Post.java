package com.blogging.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.blogging.constant.BlogConstant;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "TEXT")
	private String title;

	@Column(columnDefinition = "TEXT")
	private String details;

	private LocalDateTime time;

	private Integer isApproved;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
	private List<React> reacts;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
	private List<Comment> comments;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Integer getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Integer isApproved) {
		this.isApproved = isApproved;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<React> getReacts() {
		return reacts;
	}

	public void setReacts(List<React> reacts) {
		this.reacts = reacts;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public boolean isLikedPost(User user) {
		for (React react : reacts) {
			if (react.getReactStatus().equals(BlogConstant.LIKE_STATUS) && react.getUser().equals(user))
				return true;
		}
		return false;
	}

	public boolean isDislikedPost(User user) {
		for (React react : reacts) {
			if (react.getReactStatus().equals(BlogConstant.DISLIKE_STATUS) && react.getUser().equals(user)) {
				return true;
			}
		}
		return false;
	}

	public Integer totalLike() {
		Integer likes = 0;
		for (React react : reacts) {
			if (react.getReactStatus().equals(BlogConstant.LIKE_STATUS))
				++likes;
		}
		return likes;
	}

	public Integer totalDislike() {
		Integer dislikes = 0;
		for (React react : reacts) {
			if (react.getReactStatus().equals(BlogConstant.DISLIKE_STATUS))
				++dislikes;
		}
		return dislikes;
	}
}
