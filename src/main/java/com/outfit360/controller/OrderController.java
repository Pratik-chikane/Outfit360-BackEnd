package com.outfit360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit360.exception.OrderException;
import com.outfit360.exception.UserException;
import com.outfit360.model.Address;
import com.outfit360.model.Order;
import com.outfit360.model.User;
import com.outfit360.service.IOrderService;
import com.outfit360.service.IUserService;

@RestController
@RequestMapping("api/orders")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IUserService userService;

	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization") String jwt) throws UserException {

		User user = userService.findUserProfileByJwt(jwt);
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		Order order = orderService.createOrder(user, shippingAddress);

		System.out.println(order);
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt)
			throws UserException {
		User user = userService.findUserProfileByJwt(jwt);

		List<Order> orders = orderService.userOrderHistory(user.getId());

		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);

	}

	@GetMapping("/{Id}")
	public ResponseEntity<Order> getOrderById(@PathVariable("Id") Long orderId,
			@RequestHeader("Authorization") String jwt) throws UserException, OrderException {
		User user = userService.findUserProfileByJwt(jwt);

		Order order = orderService.findOrderById(orderId);

		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
}
