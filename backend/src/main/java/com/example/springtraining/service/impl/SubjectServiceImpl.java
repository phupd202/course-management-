package com.example.springtraining.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.constant.DateTimeConverter;
import com.example.springtraining.dto.SubjectDto;
import com.example.springtraining.entity.Assignment;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Course;
import com.example.springtraining.entity.Lecturer;
import com.example.springtraining.entity.Subject;
import com.example.springtraining.repository.AssignmentRepository;
import com.example.springtraining.repository.ClassroomRepository;
import com.example.springtraining.repository.CourseRepository;
import com.example.springtraining.repository.SubjectRepository;
import com.example.springtraining.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public List<Subject> findSubjectsByCourseId(Long courseId) {
        List<Subject> subjects = courseRepository.findSubjectsByCourseId(courseId);
        if (subjects == null || subjects.isEmpty()) {
            throw new NullPointerException("Dữ liệu trong db trống");
        }
        return subjects;
    }

    @Override
    @Transactional
    public Subject saveSubject(Subject subject) {
        if (subject == null) {
            throw new NullPointerException("Data null");
        }
        return subjectRepository.save(subject);
    }

    @Override
    public void softDelete(Long subjectId, Boolean isDeleted) {
        Subject subject = subjectRepository.findBySubjectId(subjectId);

        if (subject == null) {
            return;
        }
        subject.setIsDeleted(isDeleted);
        subjectRepository.save(subject);
    }

    @Override
    public Subject findSubjectById(Long subjectId) {
        return subjectRepository.findBySubjectId(subjectId);
    }

    @Override
    public List<SubjectDto> getSubjectOfClass(Long classroomId) {
        Objects.requireNonNull(classroomId, "ClassroomId is null!");

        Classroom classroom = classroomRepository.findByClassroomId(classroomId);
        if(classroom == null) {
            throw new NullPointerException("classroom is null!");
        }
        
        Course course = classroom.getCourse();

        List<Subject> subjects = course.getSubjects();
        
        if (subjects.isEmpty() || subjects == null) {
            return Collections.emptyList();
        } 

        return subjects.stream()
                .map(subject -> getAssignmentOfSubject(subject, classroomId))
                .collect(Collectors.toList());
    }

    // Helper for getSubjectOfClass
    private SubjectDto getAssignmentOfSubject(Subject subject, Long classroomId) {
        if(subject == null) {
            throw new NullPointerException("subject is null!");
        }
        Objects.requireNonNull(subject, "Subject is null!");

        Long subjectId = subject.getSubjectId();
        Assignment assignment = assignmentRepository.findByClassroomIdAndSubjectId(classroomId, subjectId);
        SubjectDto subjectDto = new SubjectDto();

        if (assignment != null) {
            Lecturer lecturer = assignment.getLecturer();
            
            subjectDto.setSubjectId(subjectId);
            subjectDto.setNameSubject(subject.getNameSubject());
            subjectDto.setLabel(subject.getLabel());

            subjectDto.setBeginDate(DateTimeConverter.formatLocalDate(assignment.getBeginDate()));
            subjectDto.setEndDate(DateTimeConverter.formatLocalDate(assignment.getEndDate()));

            subjectDto.setBeginTime(DateTimeConverter.formatLocalTime(assignment.getBeginTime()));
            subjectDto.setEndTime(DateTimeConverter.formatLocalTime(assignment.getEndTime()));

            subjectDto.setEstimate(subject.getEstimate());
            subjectDto.setDayOfWeek(assignment.getDayOfWeek());

            subjectDto.setLecturerId(lecturer.getLecturerId());
            subjectDto.setNameLecturer(lecturer.getNameLecture());
            subjectDto.setEmailLecturer(lecturer.getEmail());
            subjectDto.setPhoneLecturer(lecturer.getPhone());
            subjectDto.setIsClosed(assignment.getIsClosed());
        } else {
            subjectDto.setSubjectId(subjectId);
            subjectDto.setNameSubject(subject.getNameSubject());
            subjectDto.setEstimate(subject.getEstimate());
        }

        return subjectDto;
    }

}
