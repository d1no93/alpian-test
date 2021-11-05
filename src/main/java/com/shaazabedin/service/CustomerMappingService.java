package com.shaazabedin.service;

import com.shaazabedin.model.Customer;

public interface CustomerMappingService {

    String getExternalId(String customerId);

    Customer createCustomer(String customerId, String createdAt);
}
