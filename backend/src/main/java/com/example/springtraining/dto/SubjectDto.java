package com.example.springtraining.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectDto {
    private Long subjectId;

    private String nameSubject;

    private Integer estimate; // tính toán

    private String beginDate;

    private String endDate;

    private String beginTime;

    private String endTime;

    private Boolean isClosed;

    private Integer dayOfWeek;

    private Long lecturerId; // join 

    private String nameLecturer; // join 

    private String phoneLecturer;

    private String emailLecturer;

    private String label;
}
