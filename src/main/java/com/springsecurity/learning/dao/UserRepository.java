package com.springsecurity.learning.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsecurity.learning.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
}
