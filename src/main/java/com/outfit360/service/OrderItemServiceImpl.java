package com.outfit360.service;

import org.springframework.stereotype.Service;

import com.outfit360.model.OrderItem;
import com.outfit360.repository.IOrderItemRepository;

@Service
public class OrderItemServiceImpl implements IOrderItemService {

	private IOrderItemRepository orderItemRepo;

	public OrderItemServiceImpl(IOrderItemRepository orderItemRepo) {
		this.orderItemRepo = orderItemRepo;
	}

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {

		return orderItemRepo.save(orderItem);
	}

}
