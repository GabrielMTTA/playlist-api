package com.example.playlist_api.security;

import com.example.playlist.model.AppUser;
import com.example.playlist.repository.AppUserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

// Usuários para teste:  admin/admin123 (ADMIN) e user/User123 (user). 

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedUsers(AppUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                repo.save(new AppUser("admin", encoder.encode("admin123"), "ADMIN"));
            }
            if (repo.findByUsername("user").isEmpty()) {
                repo.save(new AppUser("user", encoder.encode("user123"), "USER"));
            }
        };
    }

}
