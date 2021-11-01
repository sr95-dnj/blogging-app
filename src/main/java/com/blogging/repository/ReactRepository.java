package com.blogging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogging.entity.Post;
import com.blogging.entity.React;
import com.blogging.entity.User;

@Repository
public interface ReactRepository extends JpaRepository<React, Integer> {

	React findByPostAndUser(Post post, User user);

}
