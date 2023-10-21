package com.outfit360.service;

import java.util.List;


import com.outfit360.exception.ProductException;
import com.outfit360.model.ReviewRating;
import com.outfit360.model.User;
import com.outfit360.request.ReviewRequest;

public interface IReviewRatingService {
	
	public ReviewRating createReviewRating(ReviewRequest req,User user)throws ProductException;
	
	public List<ReviewRating> getAllProductReviewRating(Long productId);

}
