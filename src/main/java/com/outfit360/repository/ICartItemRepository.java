package com.outfit360.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outfit360.model.Cart;
import com.outfit360.model.CartItem;
import com.outfit360.model.Product;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("select ci from CartItem ci where ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product") Product product,
			@Param("size") String size, @Param("userId") Long userId);

	
	public List<CartItem> findByUserId(Long userId);
}
