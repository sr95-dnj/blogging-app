package com.blogging.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogging.entity.Post;
import com.blogging.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByIsApproved(Integer isApproved);

	List<Post> findByIsApprovedAndUser(Integer pendingStatus, User user);
	
}
