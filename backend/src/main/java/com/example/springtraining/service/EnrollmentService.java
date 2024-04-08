package com.example.springtraining.service;

import java.util.List;

import com.example.springtraining.dto.lecturer.EnrollmentDto;
import com.example.springtraining.entity.Enrollment;

public interface EnrollmentService {
    List<Enrollment> findEnrollmentByClassroomId(Long classroomId);

    List<EnrollmentDto> findAssignmentByClassroomIdAndSubjectId(Long classroomId, Long subjectId);

    Enrollment findEnrollmentById(Long enrollmentId);

    Enrollment save(Enrollment enrollment);
}
