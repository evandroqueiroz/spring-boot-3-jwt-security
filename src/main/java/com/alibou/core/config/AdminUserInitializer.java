package com.alibou.core.config;

import com.alibou.core.security.auth.AuthenticationService;
import com.alibou.core.security.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.alibou.core.security.user.Role.ADMIN;
import static com.alibou.core.security.user.Role.MANAGER;

@Configuration
public class AdminUserInitializer {

  @Bean
  public CommandLineRunner commandLineRunner(AuthenticationService service) {
    return args -> {

      // ADMIN
      if (!service.userExists("admin@mail.com")) {
        var admin = RegisterRequest.builder()
            .firstname("Admin")
            .lastname("Admin")
            .email("admin@mail.com")
            .password("password")
            .role(ADMIN)
            .build();
        System.out.println("Admin token: " + service.register(admin).getAccessToken());
      } else {
        System.out.println("Admin já existe, não será recriado.");
      }

      // MANAGER
      if (!service.userExists("manager@mail.com")) {
        var manager = RegisterRequest.builder()
            .firstname("Manager")
            .lastname("Manager")
            .email("manager@mail.com")
            .password("password")
            .role(MANAGER)
            .build();
        System.out.println("Manager token: " + service.register(manager).getAccessToken());
      } else {
        System.out.println("Manager já existe, não será recriado.");
      }
    };
  }
}
