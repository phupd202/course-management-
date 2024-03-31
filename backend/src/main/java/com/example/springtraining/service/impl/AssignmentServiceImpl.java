package com.example.springtraining.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.springtraining.dto.lecturer.AssignmentLecturerDto;
import com.example.springtraining.entity.Assignment;
import com.example.springtraining.mapper.LectureAssignmentMapper;
import com.example.springtraining.repository.AssignmentRepository;
import com.example.springtraining.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository){
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public Assignment getByClassroomIdAndSubjectId(Long classroomId, Long subjectId) {
        if(classroomId == null || subjectId == null) {
            throw new NullPointerException("ClassroomId and subjectId null!");
        }
        return assignmentRepository.findByClassroomIdAndSubjectId(classroomId, subjectId);
    }

    @Override
    public List<AssignmentLecturerDto> findAssignmentOfEmailLecturer(String email) {
        if(email == null) {
            throw new NullPointerException("Email null");
        }

        List<Assignment> assignmentOfLecturer = assignmentRepository.findAssignmentByEmail(email);
        return assignmentOfLecturer.stream()
                                    .map(LectureAssignmentMapper :: mapToDto)
                                    .collect(Collectors.toList());
    }
}
