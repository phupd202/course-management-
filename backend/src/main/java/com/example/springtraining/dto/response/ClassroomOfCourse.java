package com.example.springtraining.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ClassroomOfCourse {
    private Long courseId;
    
    private String nameCourse;

    private Long classroomId;

    private String beginDate;

    private String endDate;

    private String description;
}
