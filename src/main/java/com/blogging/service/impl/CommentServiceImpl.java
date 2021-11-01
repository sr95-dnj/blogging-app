package com.blogging.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.entity.Comment;
import com.blogging.repository.CommentRepository;
import com.blogging.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public Comment findById(Integer id) {
		return commentRepository.getOne(id);
	}

	@Override
	public void save(Comment comment) {
		commentRepository.save(comment);
	}

	@Override
	public void deleteById(Integer id) {
		commentRepository.deleteById(id);
	}

}
