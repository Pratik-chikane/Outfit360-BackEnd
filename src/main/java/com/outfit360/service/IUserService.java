package com.outfit360.service;

import java.util.List;

import com.outfit360.exception.UserException;
import com.outfit360.model.User;

public interface IUserService {
	 
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public List<User> getAllUsers() throws UserException;
}
