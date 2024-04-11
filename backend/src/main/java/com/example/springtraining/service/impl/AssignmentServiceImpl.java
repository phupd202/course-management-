package com.example.springtraining.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.springtraining.dto.CheckAssignmentDto;
import com.example.springtraining.dto.lecturer.AssignmentLecturerDto;
import com.example.springtraining.entity.Assignment;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Lecturer;
import com.example.springtraining.entity.Subject;
import com.example.springtraining.mapper.LectureAssignmentMapper;
import com.example.springtraining.repository.AssignmentRepository;
import com.example.springtraining.repository.ClassroomRepository;
import com.example.springtraining.repository.LecturerRepository;
import com.example.springtraining.repository.SubjectRepository;
import com.example.springtraining.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;

    private final LecturerRepository lecturerRepository;

    private final SubjectRepository subjectRepository;

    private final ClassroomRepository classroomRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, LecturerRepository lecturerRepository, SubjectRepository subjectRepository, ClassroomRepository classroomRepository){
        this.assignmentRepository = assignmentRepository;
        this.lecturerRepository = lecturerRepository;
        this.subjectRepository = subjectRepository;
        this.classroomRepository = classroomRepository;
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

    @Override
    public Assignment updateAssignment(CheckAssignmentDto checkAssignmentDto) {
        if(checkAssignmentDto == null) {
            throw new NullPointerException("checkAssignmentDto is null!");
        } else {
            Long lecturerId = checkAssignmentDto.getLecturerId();
            Lecturer lecturer = lecturerRepository.findByLecturerId(lecturerId);

            Long subjectId = checkAssignmentDto.getSubjectId();
            Subject subject = subjectRepository.findBySubjectId(subjectId);

            Long classroomId = checkAssignmentDto.getClassroomId();
            Classroom classroom = classroomRepository.findByClassroomId(classroomId);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime beginDateFormatted = LocalDateTime.parse(checkAssignmentDto.getBeginDate(), dateFormatter);
            LocalDateTime endDateFormatted = LocalDateTime.parse(checkAssignmentDto.getEndDate(), dateFormatter);


            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime beginTimeFormatted = LocalTime.parse(checkAssignmentDto.getBeginTime(), timeFormatter);
            LocalTime endTimeFormatted = LocalTime.parse(checkAssignmentDto.getEndTime(), timeFormatter);
            
            Assignment assignment = new Assignment();
            assignment.setBeginDate(beginDateFormatted);
            assignment.setEndDate(endDateFormatted);

            assignment.setBeginTime(beginTimeFormatted);
            assignment.setEndTime(endTimeFormatted);

            assignment.setIsClosed(false);
            assignment.setDayOfWeek(checkAssignmentDto.getDayOfWeek());

            // set subject, lecturer and save assignment
            try {
                assignment.setLecturer(lecturer);
                assignment.setSubject(subject);
                assignment.setClassroom(classroom);
                // lecturerRepository.save(lecturer);
                // classroomRepository.save(classroom);
                // subjectRepository.save(subject);
                assignmentRepository.save(assignment);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return assignment;
        }
    }
}
