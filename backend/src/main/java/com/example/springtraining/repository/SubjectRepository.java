package com.example.springtraining.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springtraining.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Course c JOIN c.subjects s WHERE c.courseId = :courseId")
    List<Subject> findSubjectsByCourseId(Long courseId);

    Subject findBySubjectId(Long subjectId);
}
