package com.shaazabedin.service;

import com.shaazabedin.db.CustomerMapperRepository;
import com.shaazabedin.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.UUID;

@Slf4j
@Service
public class CustomerMappingServiceImpl implements CustomerMappingService {

    private final CustomerMapperRepository customerMapperRepository;

    public CustomerMappingServiceImpl(CustomerMapperRepository customerMapperRepository) {
        this.customerMapperRepository = customerMapperRepository;
    }

    @Override
    public String getExternalId(String customerId) {
        return null;
    }

    private Customer generateCustomer(String customerId, String createdAt) {
        String externalId = String.valueOf(UUID.randomUUID());
        Date createdAtDate = Date.valueOf(createdAt);
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setExternalId(externalId);
        customer.setCreatedAt(createdAtDate);
        return customer;
    }

    private Date validateDateFormat(String createdAt) {
        if (!createdAt.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException(String.format("Date: %s must be in yyyy-MM-dd format.", createdAt));
        }
        return Date.valueOf(createdAt);
    }

    @Override
    public Customer createCustomer(String customerId, String createdAt) {
        validateDateFormat(createdAt);
        Customer customer = this.generateCustomer(customerId, createdAt);
        log.info("Begin - Save Customer: {}", customer);
        this.customerMapperRepository.save(customer);
        log.info("End - Save Customer: {}", customer);

        return customer;
    }
}
