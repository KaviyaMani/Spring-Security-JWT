package com.nila.security_jwt.service;

import com.nila.security_jwt.entity.User;
import com.nila.security_jwt.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitializeDB {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            User user1 = User.builder()
                    .name("user")
                    .password(passwordEncoder.encode("user"))
                    .email("user@gmail.com")
                    .roles("USER")
                    .build();
            User admin = User.builder()
                    .name("admin")
                    .password(passwordEncoder.encode("admin"))
                    .email("admin@gmail.com")
                    .roles("ADMIN")
                    .build();
            userRepository.save(user1);
            userRepository.save(admin);
        };
    }
}
