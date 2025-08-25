package com.examly.springapp.config;

import com.examly.springapp.model.User;
import com.examly.springapp.model.enums.Role;
import com.examly.springapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class AppStartupConfig {

    @Bean
    CommandLineRunner init(UserRepository userRepo) {
        return args -> {
            if (!userRepo.existsByRole(Role.ADMIN)) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@gym.com");
                admin.setPassword("admin@123");
                admin.setPhoneNumber("9876543210");
                admin.setRole(Role.ADMIN);
                admin.setJoinDate(LocalDate.now());
                admin.setApproved(true);

                userRepo.save(admin);
                System.out.println("✅ Default ADMIN account created: username=admin, email=admin@gym.com");
            }
        };
    }
}
