package com.example.complexlab.core.service;

import com.example.complexlab.core.model.AppUser;

public interface UserService {
    AppUser register(String username, String rawPassword);
    boolean existsByUsername(String username);
}
