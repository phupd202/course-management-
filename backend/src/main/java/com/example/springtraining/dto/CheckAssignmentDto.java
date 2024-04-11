package com.example.springtraining.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CheckAssignmentDto {
    private String beginDate;

    private String endDate;
    
    private String beginTime;
    
    private String endTime;
    
    private Integer dayOfWeek;
    
    private Long subjectId;
    
    private Long lecturerId;

    private Long classroomId;
}
