package com.blogging.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.entity.Post;
import com.blogging.entity.React;
import com.blogging.entity.User;
import com.blogging.repository.PostRepository;
import com.blogging.repository.ReactRepository;
import com.blogging.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ReactRepository reactRepository;

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public Post findById(Integer id) {
		return postRepository.getOne(id);
	}

	@Override
	public void save(Post post) {
		postRepository.save(post);
	}

	@Override
	public void deleteById(Integer id) {
		postRepository.deleteById(id);
	}

	@Override
	public List<Post> findByIsApproved(Integer isApproved) {
		return postRepository.findByIsApproved(isApproved);
	}

	@Override
	public List<Post> findByIsApprovedAndUser(Integer pendingStatus, User user) {
		return postRepository.findByIsApprovedAndUser(pendingStatus, user);
	}

	@Override
	public void reactPost(Post post, User user, Integer reactStatus) {
		React react = reactRepository.findByPostAndUser(post, user);
		if (react == null) {
			react = new React();
			react.setPost(post);
			react.setUser(user);
			react.setTime(LocalDateTime.now());
		}
		react.setReactStatus(reactStatus);
		reactRepository.save(react);
	}

}
