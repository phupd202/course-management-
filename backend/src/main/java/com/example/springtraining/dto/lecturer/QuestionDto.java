package com.example.springtraining.dto.lecturer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionDto {
    private Long questionId;

    private String nameQuestion;

    private String nameLecturer;

    private String firstAnswer;

    private String secondAnswer;

    private String thirdAnswer;

    private String fourthAnswer;

    private String correctAnswer;

    private Long lecturerId;

    private Long subjectId;
}
