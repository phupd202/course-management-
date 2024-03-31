package com.example.springtraining.dto.lecturer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ScoreDto {
    private Long enrollmentId; 

    private Integer score;

    private String status;
}
