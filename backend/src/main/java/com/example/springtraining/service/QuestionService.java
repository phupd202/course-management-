package com.example.springtraining.service;

import java.util.List;

import com.example.springtraining.dto.lecturer.QuestionDto;
import com.example.springtraining.entity.Question;

public interface QuestionService {
    List<QuestionDto> getQuestionOfSubject(Long subjectId);
}
