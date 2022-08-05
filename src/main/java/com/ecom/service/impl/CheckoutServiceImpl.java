package com.ecom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.dto.PaymentInfo;
import com.ecom.dto.Purchase;
import com.ecom.dto.PurchaseResponse;
import com.ecom.entity.Customer;
import com.ecom.entity.OrderItem;
import com.ecom.entity.Order;
import com.ecom.repo.CustomerRepo;
import com.ecom.service.CheckoutService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	
	
	public CheckoutServiceImpl(CustomerRepo customerRepo, @Value("${stripe.key.secret}") String secretKey) {
		super();
		this.customerRepo = customerRepo;
		Stripe.apiKey = secretKey;
	}

	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {
		
		//retrieve order info from dto
		Order order = purchase.getOrder();
		
		//generate tracking number
		String orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);
		
		//populate order with orderItems
		Set<OrderItem> orderItems = purchase.getOrderItems();
		orderItems.forEach(item -> order.add(item));
		
		//populate order with shippingAddress
		order.setShippingAddress(purchase.getShippingAddress());
		
		//populate customer with order
		Customer customer = purchase.getCustomer();
		customer.add(order);
		
		//check if this is an existing customer
		String theEmail = customer.getEmail();
		Customer customerFrmDB = customerRepo.findByEmail(theEmail);
		if(customerFrmDB != null) {
			customer = customerFrmDB;
		}
		customer.add(order);
		
		//save to db
		customerRepo.save(customer);
		
		//return 
		return new PurchaseResponse(orderTrackingNumber);
	}

	private String generateOrderTrackingNumber() {
		//generate random uuid
		return UUID.randomUUID().toString();
	}

	@Override
	public PaymentIntent creatPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
		
		List<String> paymentMethodTypes = new ArrayList<>();
		paymentMethodTypes.add("card");
		
		Map<String, Object> params = new HashMap<>();
		params.put("amount", paymentInfo.getAmount());
		params.put("currency", paymentInfo.getCurrency());
		params.put("payment_method_types", paymentMethodTypes);
		params.put("receipt_email", paymentInfo.getReceiptEmail());
		return PaymentIntent.create(params);
	}

}
