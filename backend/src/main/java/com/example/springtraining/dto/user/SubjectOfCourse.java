package com.example.springtraining.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectOfCourse {
    private Long courseId; 

    private Long subjectId;

    private String nameSubject;

    private String content;

    private String label;
}
