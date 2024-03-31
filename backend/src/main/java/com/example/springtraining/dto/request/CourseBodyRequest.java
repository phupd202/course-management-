package com.example.springtraining.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseBodyRequest {
    private Boolean isClosed;

    private Long courseId;
}
