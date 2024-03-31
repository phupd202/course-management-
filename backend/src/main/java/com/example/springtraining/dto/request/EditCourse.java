package com.example.springtraining.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EditCourse {
    private Long courseId;

    private String nameCourse;

    private String url;

    private String description;
}
