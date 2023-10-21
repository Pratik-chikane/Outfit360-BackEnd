package com.outfit360.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.outfit360.exception.CartItemException;
import com.outfit360.exception.UserException;
import com.outfit360.model.Cart;
import com.outfit360.model.CartItem;
import com.outfit360.model.Product;
import com.outfit360.model.User;
import com.outfit360.repository.ICartItemRepository;
import com.outfit360.repository.ICartRepository;

@Service
public class CartItemServiceImpl implements ICartItemService {

	private ICartItemRepository cartItemRepo;

	private IUserService userService;

	private ICartRepository cartRepo;

	public CartItemServiceImpl(ICartItemRepository cartItemRepo, IUserService userService, ICartRepository cartRepo) {
		this.cartItemRepo = cartItemRepo;
		this.userService = userService;
		this.cartRepo = cartRepo;
	}

	@Override
	public CartItem createCartItem(CartItem cartItem) {

		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
		System.out.println("$$$$$$$$$$$$"+cartItem.getProduct().getDiscountedPrice());
		CartItem createdCartItem = cartItemRepo.save(cartItem);
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem item = findCartItemById(id);
		User user = userService.findUserById(userId);

		if (user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity() * item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
		}

		return cartItemRepo.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

		CartItem cartItem = cartItemRepo.isCartItemExist(cart, product, size, userId);

		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long CartItemId) throws CartItemException, UserException {
		CartItem cartItem = findCartItemById(CartItemId);
		User user = userService.findUserById(userId);
		User reqUser = userService.findUserById(cartItem.getUserId());
		if (user.getId().equals(reqUser.getId())) {
			cartItemRepo.deleteById(CartItemId);
			System.out.println("CART ITEM REMOVED");
		} else {
			throw new UserException("You Can't Remove Other User's Item ");
		}

	}

	@Override
	public CartItem findCartItemById(Long CartItemId) throws CartItemException {
		Optional<CartItem> optional = cartItemRepo.findById(CartItemId);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new CartItemException("Cart Item Not Found With Id " + CartItemId);
	}

}
