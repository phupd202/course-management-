package com.example.springtraining.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springtraining.entity.Course;
import com.example.springtraining.entity.Subject;
import com.example.springtraining.repository.CourseRepository;
import com.example.springtraining.repository.SubjectRepository;
import com.example.springtraining.service.AssignSubjectService;

@Service
public class AssignSubjectServiceImpl implements AssignSubjectService {
    private final CourseRepository courseRepository;

    private final SubjectRepository subjectRepository;

    public AssignSubjectServiceImpl(CourseRepository courseRepository, SubjectRepository subjectRepository) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> findSubjectByClassroomId(Long classroomId) {
        if(classroomId == null) {
            throw new NullPointerException("classroomId null");
        }
        Course course = courseRepository.findByClassroomId(classroomId);

        if(course == null) {
            throw new NullPointerException("Course null");
        }

        Long courseId = course.getCourseId();
        return subjectRepository.findSubjectsByCourseId(courseId);
    }

}
