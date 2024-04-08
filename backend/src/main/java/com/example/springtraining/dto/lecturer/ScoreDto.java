package com.example.springtraining.dto.lecturer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ScoreDto {
    private Long subjectId; 
    
    private Long classroomId; 

    private List<Long> enrollmentIds;

    private List<Integer> scores;

    private List<String> statues;
}
