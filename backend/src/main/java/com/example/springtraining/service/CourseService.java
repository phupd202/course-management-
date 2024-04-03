package com.example.springtraining.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.springtraining.dto.response.CourseResponse;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Course;

public interface CourseService {
    Course save(Course course);

    Page<CourseResponse> findAllCourse(Pageable pageable);

    Course findByCourseId(Long courseId);

    List<Course> findAllCourse();

    List<Classroom> findClassroomByCourseId(Long courseId);

    Course findCourseByClassroomId(Long classroomId);

    List<Course> getAllCourse();
}
