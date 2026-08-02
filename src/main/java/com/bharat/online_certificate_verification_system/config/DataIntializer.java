package com.bharat.online_certificate_verification_system.config;

import com.bharat.online_certificate_verification_system.entity.User;
import com.bharat.online_certificate_verification_system.enums.Role;
import com.bharat.online_certificate_verification_system.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataIntializer implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void run(String... args){
        if(!userRepository.existsByEmail("admin@ocvs.com")){
            User admin =User.builder()
                    .fullName("System Admin")
                    .email("admin@ocvs.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();

            userRepository.save(admin);

            System.out.println("Default Admin Created");
        }
    }
}
