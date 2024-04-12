package com.retailer.rewards.error;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException{
	
	public CustomerNotFoundException(String customerId) {
        super("Customer not found : " + customerId);
    }

}
