package com.shaazabedin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories("com.shaazabedin.db")
public class ApplicationConfig {

}
