package com.example.springtraining.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.config.jwt.JwtUtils;
import com.example.springtraining.config.service.UserDetailsImpl;
import com.example.springtraining.dto.SigninDto;
import com.example.springtraining.dto.SignupDto;
import com.example.springtraining.entity.User;
import com.example.springtraining.exception.FormatException;
import com.example.springtraining.payload.response.JwtResponse;
import com.example.springtraining.payload.response.MessageResponse;
import com.example.springtraining.service.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // @PostMapping("/course-management/signup")
    // public Optional<User> getAllEnrollmentOfUser(@RequestBody SignupDto user) throws FormatException {
    //    return userService.saveUser(user);
    // }

    // @PostMapping("/course-management/login")
    // @ResponseBody
    // public ResponseEntity<?> signIn(SigninDto request) {
    //     if (request.getEmail() == null || request.getEmail().isEmpty()) {
    //         return ResponseEntity.badRequest().body("Email is required");
    //     }

    //     User user = userService.findByEmailUser(request.getEmail());

    //     if (user != null) {
    //         String role = user.getRole();
    //         return ResponseEntity.ok(role);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @PostMapping("/course-management/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDto signupDto) throws FormatException {
        String email = signupDto.getEmail();

        if(userService.existsByEmail(email)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User is already taken!"));
        }

        // Create new user's account
        if(!signupDto.getConfirmPassword().equals(signupDto.getPassword())) {
            return ResponseEntity
            .badRequest()
            .body(new MessageResponse("password and confirm password are not matching!"));
        }

        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setPhone(signupDto.getPhone());
        user.setAddress(signupDto.getAddress());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setRole("USER");

        userService.saveUser(signupDto);
        return ResponseEntity.ok(new MessageResponse("User register successfuly!"));
    }

    @PostMapping("/course-management/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninDto signupDto ) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signupDto.getEmail(), signupDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                                        .map(item -> item.getAuthority())
                                        .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUserId(), userDetails.getUsername(), roles));
    }

}
