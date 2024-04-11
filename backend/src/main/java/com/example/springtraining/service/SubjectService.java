package com.example.springtraining.service;

import java.util.List;

import com.example.springtraining.dto.SubjectDto;
import com.example.springtraining.entity.Subject;

public interface SubjectService {
    List<Subject> findSubjectsByCourseId(Long courseId);

    Subject saveSubject(Subject subject);

    void softDelete(Long subjectId, Boolean isDeleted);

    Subject findSubjectById(Long subjectId);

    List<SubjectDto> getSubjectOfClass(Long classroomId);
}
