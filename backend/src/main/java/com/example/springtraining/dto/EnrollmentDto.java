package com.example.springtraining.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {
    private String className; 

    private LocalDateTime startDay;

    private LocalDateTime endDay;

    private LocalDateTime examDate;

    private String studyTime;

    private Integer score;

    private Integer restudy;

    private Boolean isLearning;
}
