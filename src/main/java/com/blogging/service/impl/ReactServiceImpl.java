package com.blogging.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogging.entity.Post;
import com.blogging.entity.React;
import com.blogging.entity.User;
import com.blogging.repository.ReactRepository;
import com.blogging.service.ReactService;

@Service
public class ReactServiceImpl implements ReactService {

	@Autowired
	private ReactRepository reactRepository;

	@Override
	public List<React> findAll() {
		return reactRepository.findAll();
	}

	@Override
	public React findById(Integer id) {
		return reactRepository.getOne(id);
	}

	@Override
	public void save(React react) {
		reactRepository.save(react);
	}

	@Override
	public void deleteById(Integer id) {
		reactRepository.deleteById(id);
	}

	@Override
	public React findByPostAndUser(Post post, User user) {
		return reactRepository.findByPostAndUser(post, user);
	}

}
