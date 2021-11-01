package com.blogging.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogging.constant.BlogConstant;
import com.blogging.entity.User;
import com.blogging.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		CustomUserDetails customUserDetails = null;
		if (user != null && user.getIsApproved().equals(BlogConstant.APPROVED_STATUS)) {
			customUserDetails = new CustomUserDetails();
			customUserDetails.setUser(user);
		} else {
			throw new UsernameNotFoundException("User doesn't exist with the username: " + username);
		}
		return customUserDetails;
	}

}
