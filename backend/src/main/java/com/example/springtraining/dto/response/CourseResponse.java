package com.example.springtraining.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseResponse {
    private Long courseId;

    private String nameCourse;

    private String createdAt;

    private Integer numClass;

    private Boolean isClosed;

    private String url;

    private String description;

    private Double price;
}


