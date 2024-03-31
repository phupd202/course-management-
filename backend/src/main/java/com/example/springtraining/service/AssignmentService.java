package com.example.springtraining.service;

import java.util.List;

import com.example.springtraining.dto.lecturer.AssignmentLecturerDto;
import com.example.springtraining.entity.Assignment;

public interface AssignmentService {
    Assignment getByClassroomIdAndSubjectId(Long classroomId, Long subjectId);

    List<AssignmentLecturerDto> findAssignmentOfEmailLecturer(String email);

}