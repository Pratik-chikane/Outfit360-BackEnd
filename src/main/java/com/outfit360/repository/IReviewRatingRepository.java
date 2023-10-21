package com.outfit360.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outfit360.model.ReviewRating;

public interface IReviewRatingRepository extends JpaRepository<ReviewRating, Long> {
	

	@Query("select r from ReviewRating r where r.product.id=:productId")
	public List<ReviewRating> getAllProductReviewRating(@Param("productId") Long productId);
	
	
}
