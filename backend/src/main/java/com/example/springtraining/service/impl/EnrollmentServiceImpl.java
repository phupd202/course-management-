package com.example.springtraining.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springtraining.dto.lecturer.EnrollmentDto;
import com.example.springtraining.entity.Enrollment;
import com.example.springtraining.mapper.lecturer.EnrollmentMapper;
import com.example.springtraining.repository.EnrollmentRepository;
import com.example.springtraining.service.EnrollmentService;

import jakarta.transaction.Transactional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public List<Enrollment> findEnrollmentByClassroomId(Long classroomId) {
        if (classroomId == null) {
            throw new NullPointerException("ClassroomId null!!");
        }
        return enrollmentRepository.findEnrollmentByClassroomId(classroomId);
    }

    @Override
    public List<EnrollmentDto> findAssignmentByClassroomId(Long classroomId) {
        if (classroomId == null) {
            throw new NullPointerException("ClassroomId is null!!");
        }

        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentByClassroomId(classroomId);

        return enrollments.stream()
                .map(EnrollmentMapper::mapToDto) // Sử dụng map thay vì filter
                .collect(Collectors.toList());
    }

    @Override
    public Enrollment findEnrollmentById(Long enrollmentId) {
        return enrollmentRepository.findByEnrollmentId(enrollmentId);
    }

    @Override
    @Transactional
    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
}
