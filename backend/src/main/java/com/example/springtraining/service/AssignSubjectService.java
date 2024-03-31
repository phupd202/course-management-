package com.example.springtraining.service;

import java.util.List;

import com.example.springtraining.entity.Subject;

public interface AssignSubjectService {
    List<Subject> findSubjectByClassroomId(Long classroomId);
}
