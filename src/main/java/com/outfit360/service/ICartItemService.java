package com.outfit360.service;

import com.outfit360.exception.CartItemException;
import com.outfit360.exception.UserException;
import com.outfit360.model.Cart;
import com.outfit360.model.CartItem;
import com.outfit360.model.Product;

public interface ICartItemService {
	public CartItem createCartItem(CartItem cartItem);

	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

	public void removeCartItem(Long userId, Long CartItemId) throws CartItemException, UserException;

	public CartItem findCartItemById(Long CartItemId) throws CartItemException;
}
