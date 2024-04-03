package com.example.springtraining.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.dto.response.CourseResponse;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Course;
import com.example.springtraining.mapper.CourseResponseMapper;
import com.example.springtraining.repository.CourseRepository;
import com.example.springtraining.service.CourseService;

import jakarta.validation.ValidationException;

@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Course save(Course course) {
       if(!isValidation(course)) {
            throw new ValidationException("Dữ liệu khoá học không hợp lệ!");
       }
       courseRepository.save(course);
            logger.debug("CourseService: " + course.toString());
            return course;
    }

    private Boolean isValidation(Course course) {
        return course != null && course.getNameCourse() != null && !course.getNameCourse().isEmpty();
    }

    @Override
    public Page<CourseResponse> findAllCourse(Pageable pageable) {
        Page<Course> courses =  courseRepository.findAllCourse(pageable);
        return courses.map(course -> CourseResponseMapper.mapToDto(course));
    }

    @Override
    public Course findByCourseId(Long courseId) {
        if(courseId == null || courseId < 0) {
            throw new ValidationException("CourseId = null hoặc nhỏ hơn )");
        }
        return courseRepository.findByCourseId(courseId);
    }

    @Override
    public List<Course> findAllCourse() {
        return courseRepository.findAllCourse();
    }

    @Override
    public List<Classroom> findClassroomByCourseId(Long courseId) {
        return courseRepository.findClassroomByCourseId(courseId);
    }

    @Override
    public Course findCourseByClassroomId(Long classroomId) {
        if(classroomId == null) {
            throw new ValidationException("Classroom Id null!");
        }
        return courseRepository.findByClassroomId(classroomId);
    }

    @Override
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }
}
