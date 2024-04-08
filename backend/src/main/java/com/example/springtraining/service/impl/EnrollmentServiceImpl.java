package com.example.springtraining.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springtraining.dto.lecturer.EnrollmentDto;
import com.example.springtraining.entity.Enrollment;
import com.example.springtraining.entity.Score;
import com.example.springtraining.mapper.lecturer.EnrollmentMapper;
import com.example.springtraining.repository.EnrollmentRepository;
import com.example.springtraining.repository.ScoreRepository;
import com.example.springtraining.service.EnrollmentService;

import jakarta.transaction.Transactional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public List<Enrollment> findEnrollmentByClassroomId(Long classroomId) {
        if (classroomId == null) {
            throw new NullPointerException("ClassroomId null!!");
        }
        return enrollmentRepository.findEnrollmentByClassroomId(classroomId);
    }

    @Override
    public List<EnrollmentDto> findAssignmentByClassroomIdAndSubjectId(Long classroomId, Long subjectId) {
        if (classroomId == null) {
            throw new NullPointerException("CLassroomId null");
        }

        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentByClassroomId(classroomId);

        List<EnrollmentDto> enrollmentDtos = enrollments.stream()
                .map(EnrollmentMapper::mapToDto) 
                .collect(Collectors.toList());

        for (int i = 0; i < enrollments.size(); i++) {
            Long userId = enrollments.get(i).getUser().getUserId();
            
            Score score = scoreRepository.findByUserIdAndSubjectIdANDClassroomId(userId, subjectId, classroomId);
            if(score != null) {
                // update score and status
                EnrollmentDto enrollmentDto = enrollmentDtos.get(i);
                enrollmentDto.setScore(score.getScore());
                enrollmentDto.setStatus(score.getStatus());
            } 
        }
        return enrollmentDtos;
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
