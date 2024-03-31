package com.example.springtraining.dto.lecturer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AssignmentLecturerDto {
    private Long classroomId;

    private Long subjectId;

    private String nameSubject;

    private String beginDate;

    private String endDate;

    private String beginTime;
    
    private String endTime;

    private String codeClassroom;

    private Integer dayOfWeek;
}
