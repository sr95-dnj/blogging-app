package com.blogging.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blogging.entity.User;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 7362861145509779822L;
	private User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(authority);
		return list;

	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
