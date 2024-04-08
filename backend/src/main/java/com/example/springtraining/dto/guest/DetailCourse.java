package com.example.springtraining.dto.guest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetailCourse {
    private Long courseId; 

    private String nameCourse;

    private String description;
}
