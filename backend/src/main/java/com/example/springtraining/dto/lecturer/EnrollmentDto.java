package com.example.springtraining.dto.lecturer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EnrollmentDto {
    private Long enrollmentId;

    private String name;

    private String address;

    private String birthday;

    private String status;

    private Integer score;
}
