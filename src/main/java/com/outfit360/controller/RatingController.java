//package com.outfit360.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.outfit360.exception.ProductException;
//import com.outfit360.exception.UserException;
//import com.outfit360.model.Rating;
//import com.outfit360.model.User;
//import com.outfit360.request.RatingRequest;
//import com.outfit360.service.IRatingService;
//import com.outfit360.service.IUserService;
//
//@RestController
//@RequestMapping("/api/ratings")
//public class RatingController {
//
//	@Autowired
//	private IUserService userService;
//	@Autowired
//	private IRatingService ratingService;
//
//	@PostMapping("/create")
//	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
//			@RequestHeader("Authorization") String jwt) throws ProductException, UserException {
//		User user = userService.findUserProfileByJwt(jwt);
//		Rating rating = ratingService.createRating(req, user);
//		return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
//	}
//
//	@GetMapping("/product/{productId}")
//	public ResponseEntity<List<Rating>> getAllProductRatings(@PathVariable Long productId,
//			@RequestHeader("Authorization") String jwt) throws UserException {
//		User user = userService.findUserProfileByJwt(jwt);
//		List<Rating> productRatings = ratingService.getProductRating(productId);
//		return new ResponseEntity<List<Rating>>(productRatings, HttpStatus.OK);
//	}
//}
