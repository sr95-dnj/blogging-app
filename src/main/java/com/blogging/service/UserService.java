package com.blogging.service;

import java.util.List;

import com.blogging.entity.User;

public interface UserService {

	public List<User> findAll();

	public List<User> findByIsApproved(Integer isApproved);

	public User findByUsername(String username);

	public User findById(Integer id);

	public void save(User user);

	public void delete(User user);
}
