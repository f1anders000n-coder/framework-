package com.example.complexlab.persistence.service;

import com.example.complexlab.core.model.AppUser;
import com.example.complexlab.core.model.Role;
import com.example.complexlab.core.service.UserService;
import com.example.complexlab.persistence.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AppUser register(String username, String rawPassword) {
        AppUser user = new AppUser(username, passwordEncoder.encode(rawPassword), Set.of(Role.USER));
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
