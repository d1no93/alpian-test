package com.shaazabedin.exception;

public class CustomerAlreadyExistsException extends RuntimeException{

    public CustomerAlreadyExistsException(String customerId) {
        super(String.format("Customer: %s already exists", customerId));
    }
}
