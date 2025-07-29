package com.debloopers.chibchaweb.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.debloopers.chibchaweb.entity")
@EnableJpaRepositories("com.debloopers.chibchaweb.repository")
@EnableTransactionManagement
public class DomainConfig {
}
