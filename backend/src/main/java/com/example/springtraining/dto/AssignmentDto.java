package com.example.springtraining.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AssignmentDto {
    private Long classroomId;

    private Long lecturerId;

    private Long subjectId;

    private String beginTime;

    private String endTime;

    private Integer dayOfWeek;
}
