package com.blogging.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username;

	@JsonIgnore
	private String password;

	private String email;

	private String role;

	private Integer isApproved;

	private LocalDateTime time;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Post> posts;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Comment> comments;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<React> reacts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Integer isApproved) {
		this.isApproved = isApproved;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<React> getReacts() {
		return reacts;
	}

	public void setReacts(List<React> reacts) {
		this.reacts = reacts;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
}
