package com.example.springtraining.service;

import java.util.List;
import java.util.Optional;

import com.example.springtraining.dto.InfoUserDto;
import com.example.springtraining.dto.SignupDto;
import com.example.springtraining.entity.User;
import com.example.springtraining.exception.FormatException;

public interface UserService {
    Long getUserId();

    Optional<User> findByEmailUser(String email);

    Optional<User> saveUser(SignupDto user) throws FormatException;

    Boolean existsByEmail(String email);
}
