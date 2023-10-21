package com.outfit360.service;

import com.outfit360.exception.ProductException;
import com.outfit360.model.Cart;
import com.outfit360.model.User;
import com.outfit360.request.AddItemRequest;

public interface ICartService {
	
	public Cart createCart(User user);
	
	public void addItemsToCart(Long userId, AddItemRequest req)throws ProductException;
	
	public Cart findUserCart(Long userId);

}
