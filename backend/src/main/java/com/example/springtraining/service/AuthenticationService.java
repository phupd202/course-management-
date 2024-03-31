package com.example.springtraining.service;

import com.example.springtraining.entity.User;

public interface AuthenticationService {
    Boolean authenticate(String email, String password);

    String generateToken(User user);

    String getEmailFromToken(String token);
    
} 