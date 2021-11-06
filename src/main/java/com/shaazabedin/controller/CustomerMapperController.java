package com.shaazabedin.controller;

import com.shaazabedin.service.CustomerMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@RequestMapping(path = "/customerMapper")
@RestController
@EnableWebMvc
public class CustomerMapperController {

    private final CustomerMappingService customerMappingService;

    public CustomerMapperController(CustomerMappingService customerMappingService) {
        this.customerMappingService = customerMappingService;
    }

    @PostMapping(value="customer/{customerId}/createdAt/{createdAt}/create")
    @ResponseBody
        public ResponseEntity<String> createCustomer(@PathVariable(value = "customerId") String customerId,
                                                     @PathVariable(value = "createdAt") String createdAt) {
        try {
            this.customerMappingService.createCustomer(customerId, createdAt);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Created Customer - Customer: %s, CreatedAt: %s", customerId, createdAt));
        } catch (Exception e) {
            log.error("Exception: {}, CustomerId: {}, CreatedAt: {}", e.getMessage(), customerId, createdAt);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/customer/{customerId}/externalId")
    @ResponseBody
    public ResponseEntity<String> getExternalId(@PathVariable(value = "customerId") String customerId) {
        String externalId = this.customerMappingService.getExternalId(customerId);

        if (externalId == null) {
            log.warn(String.format("CustomerId: %s doesn't exist. No ExternalId returned", customerId));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(externalId);
    }
}
