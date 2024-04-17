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

    /*
     * Input: classroomId, subjectId
     * Output: Assignment have classroomId, subjecId
     */
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

    // update if existed, create if not existed assignment 
    @Override
    public Assignment updateAssignment(CheckAssignmentDto checkAssignmentDto) {
        if(checkAssignmentDto == null) {
            throw new NullPointerException("checkAssignmentDto is null!");
        } else {
            Assignment assignment = createOrUpdateAssignment(checkAssignmentDto);
            return assignment;
        }
    }

    // helper for updateAssignment
    private Assignment createOrUpdateAssignment(CheckAssignmentDto checkAssignmentDto) {
        Long subjectId = checkAssignmentDto.getSubjectId();
        Long classroomId = checkAssignmentDto.getClassroomId();
        Long lecturerId = checkAssignmentDto.getLecturerId();

        // create or update data into database
        Assignment existingAssignment = assignmentRepository.findByClassroomIdAndSubjectId(classroomId, subjectId);

        if(existingAssignment == null) {
            existingAssignment = new Assignment();
        }

        try {
            existingAssignment.setBeginDate(parseDateTime(checkAssignmentDto.getBeginDate()));
            existingAssignment.setEndDate(parseDateTime(checkAssignmentDto.getEndDate()));
            existingAssignment.setBeginTime(parseTime(checkAssignmentDto.getBeginTime()));
            existingAssignment.setEndTime(parseTime(checkAssignmentDto.getEndTime()));

            existingAssignment.setIsClosed(false);
            existingAssignment.setDayOfWeek(checkAssignmentDto.getDayOfWeek());

            existingAssignment.setLecturer(lecturerRepository.findByLecturerId(lecturerId));
            existingAssignment.setSubject(subjectRepository.findBySubjectId(subjectId));
            existingAssignment.setClassroom(classroomRepository.findByClassroomId(classroomId));

            assignmentRepository.save(existingAssignment);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return existingAssignment;
    }

     /*
     * helper for createOrUpdateAssignment
     * input: string
     * output: localdatetime
     */
    private LocalDateTime parseDateTime(String dateStr) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(dateStr, dateFormatter);
    }

    /*
     * helper for createOrUpdateAssignment
     * input: string
     * output: localtime
     */
    private LocalTime parseTime(String timeStr) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(timeStr, timeFormatter);
    }
}
