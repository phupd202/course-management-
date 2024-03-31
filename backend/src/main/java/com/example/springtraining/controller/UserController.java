package com.example.springtraining.controller;

import com.example.springtraining.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/course-management/user")

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurity.class);
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
