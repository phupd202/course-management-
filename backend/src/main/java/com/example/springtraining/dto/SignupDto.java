package com.example.springtraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupDto {
    private String email;

    private String phone; 

    private String address;

    private String password;

    private String confirmPassword;
}
