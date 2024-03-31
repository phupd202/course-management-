package com.example.springtraining.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InfoUserDto {
    private String email;

    private String phone; 

    private String address;

    private LocalDateTime createdAt;

    private Double gpa;

    private Integer termRegistereds;
}
