package com.outfit360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outfit360.exception.OrderException;
import com.outfit360.model.Order;
import com.outfit360.response.ApiResponse;
import com.outfit360.service.IOrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	@Autowired
	private IOrderService orderService;

	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler() {
		List<Order> orders = orderService.getAllOrders();
		return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> confirmOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order = orderService.confirmedOrder(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> shipOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order = orderService.shippedOrder(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliverOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order = orderService.deliveredOrder(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order = orderService.cancelledOrder(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		orderService.deleteOrder(orderId);
		ApiResponse res = new ApiResponse();
		res.setMessage("Order Deleted Successfully");
		res.setStatus(true);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
