package com.outfit360.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.outfit360.exception.OrderException;
import com.outfit360.model.CartItem;
import com.outfit360.model.Order;
import com.outfit360.model.User;
import com.outfit360.repository.ICartItemRepository;
import com.outfit360.repository.IOrderRepository;
import com.outfit360.response.ApiResponse;
import com.outfit360.response.PaymentLinkResponse;
import com.outfit360.service.ICartItemService;
import com.outfit360.service.IOrderItemService;
import com.outfit360.service.IOrderService;
import com.outfit360.service.IUserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api")
public class PaymentController {

//	@Value("${razorpay.api.key}")
//	String apiKey;
//
//	@Value("${razorpay.api.secret}")
//	String apiSecret;

	public static final String apiKey = "rzp_test_O0AOjArZQRFo3r";
	public static final String apiSecret = "g3iEVEvAuCFltdGw67vyafwc";
	@Autowired
	private IOrderService orderService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ICartItemService cartItemService;

	@Autowired
	private IOrderRepository orderRepo;

	@Autowired
	private ICartItemRepository cartItemRepo;

//	@PostMapping("/payments/{orderId}")
//	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
//			@RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {
//		Order order = orderService.findOrderById(orderId);
//
//		try {
//			System.out.println("%%%%%%%%%%%%%%%%%%%%%" + apiKey + "" + apiSecret);
//			RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
//
//			System.out.println("*************************" + razorpay);
//			JSONObject paymentLinkRequest = new JSONObject();
//			paymentLinkRequest.put("amount", order.getTotalDiscountedPrice() * 100);
//			paymentLinkRequest.put("currency", "INR");
//			paymentLinkRequest.put("callback_url", "http://localhost:3000/payments/" + orderId);
//			paymentLinkRequest.put("callback_method", "get");
//			
//			System.out.println("paymentLinkRequest"+paymentLinkRequest);
//			JSONObject customer = new JSONObject();
//			customer.put("name", order.getUser().getFirstName());
//			customer.put("email", order.getUser().getEmail());
//			paymentLinkRequest.put("customer", customer);
//			System.out.println("customer"+customer);
//
//			JSONObject notify = new JSONObject();
//			notify.put("sms", true);
//			notify.put("email", true);
//			paymentLinkRequest.put("notify", notify);
//
//			PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
//			System.out.println("PAYMENT"+payment);
//
//			String paymentLinkId = payment.get("id");
//			String paymentLinkUrl = payment.get("short_url");
//
//			PaymentLinkResponse res = new PaymentLinkResponse();
//			res.setPayment_link_id(paymentLinkId);
//			res.setPayment_link_url(paymentLinkUrl);
//			
//			System.out.println("RESPONSE"+res);
//
//			return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.CREATED);
//
//		} catch (Exception e) {
//			throw new RazorpayException(e.getMessage());
//		}
//
//	}
//
//	@GetMapping("/payments")
//	public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
//			@RequestParam(name = "order_id") Long orderId, @RequestParam(name = "payment_link_id") String paymentLinkId)
//			throws OrderException, RazorpayException {
//		Order order = orderService.findOrderById(orderId);
//
//		order.setOrderId(orderId.toString());
//		orderRepo.save(order);
//
//		RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
//		System.out.println("paymentID" + paymentId + " " + paymentLinkId);
//		try {
//
//			Payment payment = razorpay.payments.fetch(paymentId);
//			if (payment.get("status").equals("captured")) {
//				order.getPaymentDetails().setRazorpayPaymentId(paymentId);
//				order.getPaymentDetails().setPaymentMethod("CARD");
//				order.getPaymentDetails().setRazorpayPaymentLinkStatus("paid");
//				order.getPaymentDetails().setRazorpayPaymentLinkId(paymentLinkId);
//				order.getPaymentDetails().setStatus("COMPLETED");
//				order.setOrderStatus("PLACED");
//				orderRepo.save(order);
//
//			}
//			ApiResponse res = new ApiResponse();
//			res.setMessage("Your Order Has been Placed");
//			res.setStatus(true);
//			
//		
//			User user = order.getUser();
//			List<CartItem> cartItems = cartItemRepo.findByUserId(user.getId());
//
//			cartItems.forEach(cartItem->System.out.println("CARTITEMS"+cartItem));
//			for (CartItem cartItem : cartItems) {
//				cartItemService.removeCartItem(user.getId(), cartItem.getId());
//			}
//
//			return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
//		} catch (Exception e) {
//			throw new RazorpayException(e.getMessage());
//		}
//	}

	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {

		Order order = orderService.findOrderById(orderId);
		try {
			RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount", order.getTotalDiscountedPrice() * 100);
			paymentLinkRequest.put("currency", "INR");

			System.out.println("$$$$$$$$$$$$$$$$$$$");
			System.out.println("AMOUNT"+paymentLinkRequest.getInt("amount"));
			JSONObject customer = new JSONObject();
			customer.put("name", order.getUser().getFirstName());
			customer.put("email", order.getUser().getEmail());
			paymentLinkRequest.put("customer", customer);

			JSONObject notify = new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);

			paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/" + orderId);
			paymentLinkRequest.put("callback_method", "get");
			System.out.println("callback_url"+paymentLinkRequest.getString("callback_url"));
			PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

			System.out.println("PAYMENT"+payment);
			String paymentLinkId = payment.get("id");
			String paymentLinkUrl = payment.get("short_url");

			PaymentLinkResponse res = new PaymentLinkResponse();
			res.setPayment_link_id(paymentLinkId);
			res.setPayment_link_url(paymentLinkUrl);
			return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.CREATED);

		} catch (Exception e) {
			throw new RazorpayException(e.getMessage());
		}
	}
	@GetMapping("/payments")
	public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
			@RequestParam(name = "order_id") Long orderId,
			@RequestParam(name = "payment_link_id") String paymentLinkId) throws OrderException, RazorpayException {
		Order order = orderService.findOrderById(orderId);
		RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
		try {
			Payment payment =razorpay.payments.fetch(paymentId);
			if(payment.get("status").equals("captured")) {
				order.getPaymentDetails().setPaymentId(paymentId);
				order.getPaymentDetails().setStatus("COMPLETED");
				order.setOrderStatus("PLACED");
				orderRepo.save(order);
			}
			ApiResponse res = new ApiResponse();
			res.setMessage("Your Order Has been Placed");
			res.setStatus(true);
			return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
			} catch (Exception e) {
				throw new RazorpayException(e.getMessage());
			}
		}

}
