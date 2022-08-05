package com.ecom.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

	Customer findByEmail(String theEmail);
}
