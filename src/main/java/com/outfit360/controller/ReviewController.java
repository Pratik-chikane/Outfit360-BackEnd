package com.outfit360.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit360.exception.ProductException;
import com.outfit360.exception.UserException;
import com.outfit360.model.ReviewRating;
import com.outfit360.model.User;
import com.outfit360.request.ReviewRequest;
import com.outfit360.service.IReviewRatingService;
import com.outfit360.service.IUserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IReviewRatingService reviewRatingService;

	@PostMapping("/create")
	public ResponseEntity<ReviewRating> createProductReview(@RequestBody ReviewRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		System.out.println("*******************"+req.getProductId());
		System.out.println("*******************"+req.getReview());
		ReviewRating reviewRating = reviewRatingService.createReviewRating(req, user);

		return new ResponseEntity<ReviewRating>(reviewRating, HttpStatus.CREATED);

	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<ReviewRating>> getAllProductReview(@RequestHeader("Authorization") String jwt,
			@PathVariable Long productId) {
		List<ReviewRating> reviewsRatings = reviewRatingService.getAllProductReviewRating(productId);
		return new ResponseEntity<List<ReviewRating>>(reviewsRatings, HttpStatus.OK); 
	}
}
