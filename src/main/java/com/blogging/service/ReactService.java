package com.blogging.service;

import java.util.List;

import com.blogging.entity.Post;
import com.blogging.entity.React;
import com.blogging.entity.User;

public interface ReactService {

	public List<React> findAll();

	public React findById(Integer id);

	public void save(React react);

	public void deleteById(Integer id);

	public React findByPostAndUser(Post post, User user);
}
