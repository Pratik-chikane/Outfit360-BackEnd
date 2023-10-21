package com.outfit360.service;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.stereotype.Service;

import com.outfit360.exception.ProductException;
import com.outfit360.model.Product;
import com.outfit360.model.ReviewRating;
import com.outfit360.model.User;
import com.outfit360.repository.IProductRepository;
import com.outfit360.repository.IReviewRatingRepository;
import com.outfit360.request.ReviewRequest;
@Service
public class ReviewRatingServiceImpl implements IReviewRatingService {

	private IReviewRatingRepository reviewRatingRepo;
	private IProductService productService;
	private IProductRepository productRepo;



	
	public ReviewRatingServiceImpl(IReviewRatingRepository reviewRatingRepo, IProductService productService,
			IProductRepository productRepo) {
		
		this.reviewRatingRepo = reviewRatingRepo;
		this.productService = productService;
		this.productRepo = productRepo;
	}

	@Override
	public ReviewRating createReviewRating(ReviewRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		ReviewRating reviewRating = new ReviewRating();
		reviewRating.setProduct(product);
		reviewRating.setReview(req.getReview());
		reviewRating.setRating(req.getRating());
		reviewRating.setUser(user);
		reviewRating.setCreatedAt(LocalDateTime.now());
		return reviewRatingRepo.save(reviewRating);
	}

	@Override
	public List<ReviewRating> getAllProductReviewRating(Long productId) {

		return reviewRatingRepo.getAllProductReviewRating(productId);
	}

}
