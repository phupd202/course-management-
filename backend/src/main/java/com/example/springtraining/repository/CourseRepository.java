package com.example.springtraining.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Course;
import com.example.springtraining.entity.Subject;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    @Transactional
    Course save(Course course);

    @Transactional
    @Query("SELECT c FROM Course c")
    Page<Course> findAllCourse(Pageable pagebale);

    @Transactional
    Course findByCourseId(Long courseId);

    @Transactional
    @Query("SELECT s FROM Course c JOIN c.subjects s WHERE c.courseId = :courseId")
    List<Subject> findSubjectsByCourseId(Long courseId);


    @Transactional
    @Query("SELECT c FROM Course c")
    List<Course> findAllCourse();

    @Transactional
    @Query("SELECT cls FROM Course c JOIN c.classrooms cls WHERE c.courseId = :courseId")
    List<Classroom> findClassroomByCourseId(@Param("courseId") Long courseId);

    @Transactional
    @Query("SELECT c FROM Course c JOIN c.classrooms classroom WHERE classroom.classroomId = :classroomId")
    Course findByClassroomId(Long classroomId);

    @Query("SELECT c FROM Course c WHERE LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.nameCourse) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Course> findCourseByKeyword(@Param("keyword") String keyword);
}
