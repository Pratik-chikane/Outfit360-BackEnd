package com.outfit360.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outfit360.model.Cart;

public interface ICartRepository extends JpaRepository<Cart, Long> {
	
	@Query("select c from Cart c where c.user.id=:userId")
	public Cart findByUserId(@Param("userId")Long userId);

}
