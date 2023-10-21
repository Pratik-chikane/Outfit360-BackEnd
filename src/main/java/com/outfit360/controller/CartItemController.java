package com.outfit360.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit360.exception.CartItemException;
import com.outfit360.exception.UserException;
import com.outfit360.model.CartItem;
import com.outfit360.model.User;
import com.outfit360.response.ApiResponse;
import com.outfit360.service.ICartItemService;
import com.outfit360.service.IUserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/cart_item")
@ApiOperation("Cart Item Mangement")
public class CartItemController {

	@Autowired
	private ICartItemService cartItemService;

	@Autowired
	private IUserService userService;

	@DeleteMapping("/{cartItemId}")
	@ApiOperation("Remove Cart Item from Cart")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws CartItemException, UserException {
		User user = userService.findUserProfileByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("CartItem Deleted Successfully");
		apiResponse.setStatus(true);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

	}

	@PutMapping("/{cartItemId}")
	@ApiOperation("Update Item To Cart")
	public ResponseEntity<CartItem> updateCartItem(@RequestHeader("Authorization") String jwt,
			@PathVariable Long cartItemId, @RequestBody CartItem cartItem) throws CartItemException, UserException {
		User user = userService.findUserProfileByJwt(jwt);
		CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

		return new ResponseEntity<CartItem>(updatedCartItem, HttpStatus.OK);

	}
}
