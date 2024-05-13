package com.example.springtraining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.Question;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Transactional
    @Query("SELECT q FROM Question q JOIN q.subject s WHERE s.subjectId =:subjectId")
    List<Question> findAllQuestionBySubjectId(Long subjectId);

    @Transactional
    Question save(Question question);
    
}
