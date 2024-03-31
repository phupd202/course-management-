package com.example.springtraining.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentOfClassResponse {
    private Long userId; 

    private String email;

    private String address;

    private String enrollmentDay;

    private String phone;
}
