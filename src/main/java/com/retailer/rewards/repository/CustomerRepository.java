package com.retailer.rewards.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.retailer.rewards.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    public Optional<Customer> findByCustomerId(Long customerId);
}