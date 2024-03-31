package com.example.springtraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectCourseDto {
    private Long courseId;

    private String nameCourse;
}
