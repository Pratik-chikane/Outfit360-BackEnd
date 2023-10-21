package com.outfit360.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit360.config.JwtProviderService;
import com.outfit360.exception.UserException;
import com.outfit360.model.Cart;
import com.outfit360.model.User;
import com.outfit360.repository.IUserRepository;
import com.outfit360.request.LoginRequest;
import com.outfit360.response.AuthResponse;
import com.outfit360.service.CustomUserServiceImpl;
import com.outfit360.service.ICartService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private IUserRepository userRepository;
	private JwtProviderService jwtProvider;
	private PasswordEncoder passwordEncoder;
	private CustomUserServiceImpl customUserService;
	private ICartService cartService;

	public AuthController(IUserRepository userRepository, JwtProviderService jwtProvider,
			PasswordEncoder passwordEncoder, CustomUserServiceImpl customUserService, ICartService cartService) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
		this.passwordEncoder = passwordEncoder;
		this.customUserService = customUserService;
		this.cartService = cartService;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		User isEmailExist = userRepository.findByEmail(email);

		if (isEmailExist != null) {
			throw new UserException("Email Is Already Exists with Another Account");
		}

		User newUser = new User();

		newUser.setEmail(email);
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setCreatedAt(LocalDateTime.now());

		User savedUser = userRepository.save(newUser);
		Cart cart = cartService.createCart(savedUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("SignUp Success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest request) {
		String username = request.getEmail();

		String password = request.getPassword();

		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("SignIn Success");
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Email");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
