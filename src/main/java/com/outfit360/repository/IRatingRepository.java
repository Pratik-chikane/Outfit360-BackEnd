////package com.outfit360.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.outfit360.model.Rating;
//
//public interface IRatingRepository extends JpaRepository<Rating, Long> {
//	
//	@Query("select r from Rating r where r.product.id=:productId")
//	public List<Rating> getAllProductRating(@Param("productId") Long productId);
//
//}
