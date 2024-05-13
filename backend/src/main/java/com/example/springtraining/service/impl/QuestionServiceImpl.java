package com.example.springtraining.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springtraining.dto.lecturer.QuestionDto;
import com.example.springtraining.entity.Question;
import com.example.springtraining.repository.QuestionRepository;
import com.example.springtraining.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<QuestionDto> getQuestionOfSubject(Long subjectId) {
        List<Question> questionOfSubject = questionRepository.findAllQuestionBySubjectId(subjectId);

        return questionOfSubject.stream()
                                .map(question -> maptoDto(question))
                                .toList();
    }

    public QuestionDto maptoDto(Question question) {
        QuestionDto questionDto = new QuestionDto();

        questionDto.setQuestionId(question.getQuestionId());
        questionDto.setNameQuestion(question.getNameQuestion());
        questionDto.setFirstAnswer(question.getFirstAnswer());
        questionDto.setSecondAnswer(question.getSecondAnswer());
        questionDto.setThirdAnswer(question.getThirdAnswer());
        questionDto.setFourthAnswer(question.getFourthAnswer());
        questionDto.setCorrectAnswer(question.getCorrectAnswer());
        questionDto.setLecturerId(question.getLecturer().getLecturerId());
        questionDto.setSubjectId(question.getSubject().getSubjectId());
        questionDto.setNameLecturer(question.getLecturer().getNameLecture());

        return questionDto;
    }
    
}
