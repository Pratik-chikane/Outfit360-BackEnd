package com.outfit360.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit360.model.User;

public interface IUserRepository extends JpaRepository<User, Long>{
	
	public User findByEmail(String email);

}
