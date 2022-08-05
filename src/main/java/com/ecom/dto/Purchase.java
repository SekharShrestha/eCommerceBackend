package com.ecom.dto;

import java.util.Set;

import com.ecom.entity.Address;
import com.ecom.entity.Customer;
import com.ecom.entity.Order;
import com.ecom.entity.OrderItem;

public class Purchase {
	
	private Customer customer;
	private Address shippingAddress;
	private Order order;
	private Set<OrderItem> orderItems;
	
	public Purchase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Purchase(Customer customer, Address shippingAddress, Order orders, Set<OrderItem> orderItems) {
		super();
		this.customer = customer;
		this.shippingAddress = shippingAddress;
		this.order = orders;
		this.orderItems = orderItems;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "Purchase [customer=" + customer + ", shippingAddress=" + shippingAddress + ", orders=" + order
				+ ", orderItems=" + orderItems + "]";
	}
	
	

}
