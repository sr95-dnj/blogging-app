package com.blogging.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogging.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
		
	List<User> findByIsApproved(Integer isApproved);

}
