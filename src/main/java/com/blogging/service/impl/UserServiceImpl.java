package com.blogging.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.entity.User;
import com.blogging.repository.UserRepository;
import com.blogging.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User findById(Integer id) {
		return userRepository.findById(id).get();
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findByIsApproved(Integer isApproved) {
		return userRepository.findByIsApproved(isApproved);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

}
