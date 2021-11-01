package com.blogging.config;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.blogging.constant.BlogConstant;
import com.blogging.entity.User;
import com.blogging.service.UserService;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(UserCommandLineRunner.class);

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	/**
	 * generating one admin type user’s account at the application
	 * bootstrap for the first time
	 */

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("admin");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setIsApproved(BlogConstant.APPROVED_STATUS);
		user.setEmail("admin@gmail.com");
		user.setRole(BlogConstant.ROLE_ADMIN);
		user.setTime(LocalDateTime.now());
		userService.save(user);
		log.info("admin created first time.....");
		log.info("username : " + user.getUsername() + " and password : admin");
	}

}
