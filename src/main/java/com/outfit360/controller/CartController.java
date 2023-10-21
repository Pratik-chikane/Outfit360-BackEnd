package com.outfit360.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit360.exception.ProductException;
import com.outfit360.exception.UserException;
import com.outfit360.model.Cart;
import com.outfit360.model.User;
import com.outfit360.request.AddItemRequest;
import com.outfit360.response.ApiResponse;
import com.outfit360.service.ICartService;
import com.outfit360.service.IUserService;

import io.swagger.annotations.ApiOperation;

@RestController

@RequestMapping("/api/cart")
@ApiOperation("Cart Managment")
public class CartController {

	@Autowired
	private ICartService cartService;
	@Autowired
	private IUserService userService;

	@GetMapping("/")
	@ApiOperation("Find Cart By User Id")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
	
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@PutMapping("/add")
	@ApiOperation("Add Item To Cart")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestHeader("Authorization") String jwt,
			@RequestBody AddItemRequest req) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		cartService.addItemsToCart(user.getId(), req);

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Item Added To Cart Successfully");
		apiResponse.setStatus(true);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

	}

}
