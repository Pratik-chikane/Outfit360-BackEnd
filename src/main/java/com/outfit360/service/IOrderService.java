package com.outfit360.service;

import java.util.List;

import com.outfit360.exception.OrderException;
import com.outfit360.model.Address;
import com.outfit360.model.Order;
import com.outfit360.model.User;

public interface IOrderService {
	public Order createOrder(User user, Address shippingAddress);

	public Order findOrderById(Long orderId) throws OrderException;

	public List<Order> userOrderHistory(Long userId);

	public Order placeOrder(Long orderId) throws OrderException;

	public Order confirmedOrder(Long orderId) throws OrderException;

	public Order shippedOrder(Long orderId) throws OrderException;

	public Order deliveredOrder(Long orderId) throws OrderException;

	public Order cancelledOrder(Long orderId) throws OrderException;

	public List<Order> getAllOrders();

	public void deleteOrder(Long orderId) throws OrderException;

}
