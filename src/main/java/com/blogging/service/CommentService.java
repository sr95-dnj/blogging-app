package com.blogging.service;

import java.util.List;

import com.blogging.entity.Comment;

public interface CommentService {

	public List<Comment> findAll();

	public Comment findById(Integer id);

	public void save(Comment comment);

	public void deleteById(Integer id);
}
