package com.example.springtraining.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDto {
    private Long scoreId;

    private Integer score;

    private String status;

    private Long subjectId;

    private Long classroomId;
    
    private String nameSubject;
}
