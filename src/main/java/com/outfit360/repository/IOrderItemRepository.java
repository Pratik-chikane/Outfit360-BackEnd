package com.outfit360.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outfit360.model.OrderItem;

public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {

}
