package com.example.springtraining.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.springtraining.dto.admin.CourseResponse;
import com.example.springtraining.dto.response.ClassroomResponse;
import com.example.springtraining.entity.Course;

public interface CourseService {
    Course save(Course course);

    Page<CourseResponse> findAllCourse(Pageable pageable);

    Course findByCourseId(Long courseId);

    List<Course> findAllCourse();

    List<ClassroomResponse> findClassroomByCourseId(Long courseId);

    Course findCourseByClassroomId(Long classroomId);

    List<Course> getAllCourse();

    void updateCourse(CourseResponse courseResponse);

    List<CourseResponse> findCourseByKeyword(String keyword);
}
