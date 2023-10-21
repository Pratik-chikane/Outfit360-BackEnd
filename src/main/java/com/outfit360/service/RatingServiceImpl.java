//package com.outfit360.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.outfit360.exception.ProductException;
//import com.outfit360.model.Product;
//import com.outfit360.model.Rating;
//import com.outfit360.model.User;
//import com.outfit360.repository.IRatingRepository;
//import com.outfit360.request.RatingRequest;
//
//@Service
//public class RatingServiceImpl implements IRatingService {
//
//	private IRatingRepository ratingRepo;
//	private IProductService productService;
//
//	public RatingServiceImpl(IRatingRepository ratingRepo, IProductService productService) {
//		this.ratingRepo = ratingRepo;
//		this.productService = productService;
//	}
//
//	@Override
//	public Rating createRating(RatingRequest req, User user) throws ProductException {
//		Product product = productService.findProductById(req.getProductId());
//
//		Rating rating = new Rating();
//		rating.setProduct(product);
//
//		rating.setRating(req.getRating());
//		rating.setUser(user);
//		rating.setCreatedAt(LocalDateTime.now());
//		return ratingRepo.save(rating);
//	}
//
//	@Override
//	public List<Rating> getProductRating(Long productId) {
//
//		return ratingRepo.getAllProductRating(productId);
//	}
//
//}
