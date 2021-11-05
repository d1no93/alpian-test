package com.shaazabedin.db;

import com.shaazabedin.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMapperRepository extends JpaRepository<Customer, String> {
}
