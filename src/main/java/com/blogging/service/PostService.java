package com.blogging.service;

import java.util.List;

import com.blogging.entity.Post;
import com.blogging.entity.User;

public interface PostService {

	public List<Post> findAll();

	public Post findById(Integer id);

	public void save(Post post);

	public void deleteById(Integer id);
	
	public List<Post> findByIsApproved(Integer isApproved);

	public List<Post> findByIsApprovedAndUser(Integer pendingStatus, User user);

	public void reactPost(Post post, User user, Integer likeStatus);
}
