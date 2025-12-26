package com.hubunity.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.boot.autoconfigure.domain.EntityScan(basePackages = "com.hubunity.core")
@org.springframework.data.jpa.repository.config.EnableJpaRepositories(basePackages = "com.hubunity.core")
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
