package com.outfit360.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.outfit360.config.JwtProviderService;
import com.outfit360.exception.UserException;
import com.outfit360.model.User;
import com.outfit360.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {

	

	private IUserRepository userRepo;

	private JwtProviderService jwtProvider;

	public UserServiceImpl(IUserRepository userRepo, JwtProviderService jwtProvider) {
		this.userRepo = userRepo;
		this.jwtProvider = jwtProvider;
	}
	
	@Override
	public List<User> getAllUsers() throws UserException {
		List<User> users = userRepo.findAll();
		System.out.println(users);
		
		return users;
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user = userRepo.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("User Not Found With Id "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new UserException("User Not Found With Email "+email);
		}
		return user;
	}

}
