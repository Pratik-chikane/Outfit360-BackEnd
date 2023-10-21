package com.outfit360.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit360.exception.ProductException;
import com.outfit360.exception.UserException;
import com.outfit360.model.Cart;
import com.outfit360.model.CartItem;
import com.outfit360.model.Product;
import com.outfit360.model.User;
import com.outfit360.repository.ICartRepository;
import com.outfit360.request.AddItemRequest;

@Service
public class CartServiceImpl implements ICartService {

	private ICartRepository cartRepo;
	private ICartItemService cartItemService;
	private IProductService productService;

	public CartServiceImpl(ICartRepository cartRepo, ICartItemService cartItemService, IProductService productService) {
		this.cartRepo = cartRepo;
		this.cartItemService = cartItemService;
		this.productService = productService;
	}

	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
			
		return cartRepo.save(cart);
	}

	@Override
	public void addItemsToCart(Long userId, AddItemRequest req) throws ProductException {

		Cart cart = cartRepo.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());
		System.out.println("PRODUCT "+product);
		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

		if (isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
//			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
//			int price = req.getQuantity() * product.getDiscountedPrice();
			cartItem.setDiscountedPrice(100);

			cartItem.setSize(req.getSize());

			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
		}

	
	}


	@Override
	public Cart findUserCart(Long userId){
		Cart cart = cartRepo.findByUserId(userId);
		
		
		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItems = 0;
		for (CartItem cartItem : cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
			totalItems = totalItems + cartItem.getQuantity();
		}
		cart.setTotalPrice(totalPrice);
		cart.setTotalItems(totalItems);
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setDiscount(totalPrice - totalDiscountedPrice);
		return cartRepo.save(cart);
	}

}
