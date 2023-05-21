package com.keeper.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keeper.api.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
}
