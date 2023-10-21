package com.outfit360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit360.exception.UserException;
import com.outfit360.model.User;
import com.outfit360.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<User>(user, HttpStatus.OK); 
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String jwt) throws UserException{
		List<User> users = userService.getAllUsers();
		users.forEach(user -> System.out.printf("************",user));
		return new ResponseEntity<List<User>>(users,HttpStatus.ACCEPTED);
	}
}
