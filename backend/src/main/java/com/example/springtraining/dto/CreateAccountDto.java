package com.example.springtraining.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDto {
    private String email;

    private String phone;

    private Long classroomId;
    
    private Long registerId;
}
