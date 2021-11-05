package com.shaazabedin.service;

import com.shaazabedin.db.CustomerMapperRepository;
import com.shaazabedin.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;
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
        Optional<Customer> customerToFind = customerMapperRepository.findById(customerId);
        if (customerToFind.isPresent()) {
            log.info("Found Customer: {}", customerToFind);
            return customerToFind.get().getExternalId();
        } else {
            log.warn("No Customer with CustomerId: {}", customerId);
            return null;
        }
    }

    private Customer generateCustomer(String customerId, Date createdAt) {
        String externalId = String.valueOf(UUID.randomUUID());
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setExternalId(externalId);
        customer.setCreatedAt(createdAt);
        return customer;
    }

    private Date validateDateFormat(String createdAt) {
        if (!createdAt.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException(String.format("Date: %s must be in yyyy-MM-dd format.", createdAt));
        }

        Date now = new Date(Date.from(Instant.now()).getTime());

        Date createdAtDate = Date.valueOf(createdAt);

        if (now.before(createdAtDate)) {
            throw new IllegalArgumentException(String.format("Date: %s must be before today's Date (%s)", createdAtDate, now));
        }

        return createdAtDate;
    }

    @Override
    public Customer createCustomer(String customerId, String createdAt) {
        Date createdAtDate = validateDateFormat(createdAt);
        Customer customer = this.generateCustomer(customerId, createdAtDate);
        log.info("Begin - Save Customer: {}", customer);
        this.customerMapperRepository.save(customer);
        log.info("End - Save Customer: {}", customer);

        return customer;
    }
}
