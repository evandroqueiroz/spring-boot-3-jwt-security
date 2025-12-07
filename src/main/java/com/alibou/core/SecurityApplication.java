package com.alibou.core;

import com.alibou.core.security.auth.AuthenticationService;
import com.alibou.core.security.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.alibou.core.security.user.Role.ADMIN;
import static com.alibou.core.security.user.Role.MANAGER;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
