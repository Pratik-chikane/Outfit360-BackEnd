package com.outfit360.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.outfit360.exception.OrderException;
import com.outfit360.model.Address;
import com.outfit360.model.Cart;
import com.outfit360.model.CartItem;
import com.outfit360.model.Order;
import com.outfit360.model.OrderItem;
import com.outfit360.model.User;
import com.outfit360.repository.IAddressRepository;
import com.outfit360.repository.ICartRepository;
import com.outfit360.repository.IOrderItemRepository;
import com.outfit360.repository.IOrderRepository;
import com.outfit360.repository.IUserRepository;

@Service
public class OrderServiceImpl implements IOrderService {

	private IOrderRepository orderRepo;
	private ICartService cartService;
	private IAddressRepository addressRepo;
	private IOrderItemService orderItemService;
	private IOrderItemRepository orderItemRepo;
	private IUserRepository userRepo;

	public OrderServiceImpl(IOrderRepository orderRepo, ICartService cartService, IAddressRepository addressRepo,
			IOrderItemService orderItemService, IOrderItemRepository orderItemRepo, IUserRepository userRepo) {
		this.orderRepo = orderRepo;
		this.cartService = cartService;
		this.addressRepo = addressRepo;
		this.orderItemService = orderItemService;
		this.orderItemRepo = orderItemRepo;
		this.userRepo = userRepo;
	}

	@Override
	public Order createOrder(User user, Address shippingAddress) {
		shippingAddress.setUser(user);
		Optional<Address> optional = addressRepo.findById(shippingAddress.getId() != null ?shippingAddress.getId():-1);
		Address address = null;
		if(optional.isPresent()) {
			user.getAddress().add(optional.get());
			userRepo.save(user);
		}
		
		address = addressRepo.save(shippingAddress);
		user.getAddress().add(address);
		userRepo.save(user);
		

		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			System.out.println("%#########################33"+item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());

			OrderItem savedOrderItem = orderItemRepo.save(orderItem);

			orderItems.add(savedOrderItem);

		}
		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);

		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItems());
	
		createdOrder.setDeliveryAddress(address == null ? optional.get():address);
		createdOrder.setOrderDate(LocalDateTime.now());
		String status = "PENDING";
		createdOrder.setOrderStatus(status);

		createdOrder.getPaymentDetails().setStatus("PENDING");
		
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		System.out.println("created ORDER "+ createdOrder.getOrderStatus());

		Order savedOrder = orderRepo.save(createdOrder);
		
		System.out.println("SAVED ORDER "+ savedOrder.getOrderStatus());

		for (OrderItem item : orderItems) {
			item.setOrder(savedOrder);
			orderItemRepo.save(item);
		}
		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> optional = orderRepo.findById(orderId);
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new OrderException("Order Does Not Exist With Id "+orderId);
	}

	@Override
	public List<Order> userOrderHistory(Long userId) {
		List<Order> orders = orderRepo.getUserOrders(userId);
		
		return orders;
	}

	@Override
	public Order placeOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return orderRepo.save(order);
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return orderRepo.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepo.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepo.save(order);
	}

	@Override
	public Order cancelledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepo.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepo.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		orderRepo.deleteById(orderId);

	}

}
