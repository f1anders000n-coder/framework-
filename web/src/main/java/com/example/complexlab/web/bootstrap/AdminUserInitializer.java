package com.example.complexlab.web.bootstrap;

import com.example.complexlab.core.model.AppUser;
import com.example.complexlab.core.model.Role;
import com.example.complexlab.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminUserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        userRepository.findByUsername(adminUsername).ifPresentOrElse(existing -> {
            if (existing.getRoles() == null || existing.getRoles().stream().noneMatch(r -> r == Role.ADMIN)) {
                existing.getRoles().add(Role.ADMIN);
                userRepository.save(existing);
            }
        }, () -> {
            AppUser admin = new AppUser(adminUsername, passwordEncoder.encode(adminPassword), Set.of(Role.ADMIN, Role.USER));
            userRepository.save(admin);
        });
    }
}
