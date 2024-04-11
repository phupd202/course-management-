package com.example.springtraining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    @Transactional
    @Query("SELECT a FROM Assignment a JOIN a.classroom c JOIN a.subject s WHERE c.classroomId = :classroomId AND s.subjectId = :subjectId")    
    Assignment findByClassroomIdAndSubjectId(Long classroomId, Long subjectId);

    @Query("SELECT a FROM Assignment a JOIN a.lecturer lec WHERE lec.email = :email")
    List<Assignment> findAssignmentByEmail(@Param("email") String email);

    @Transactional
    List<Assignment> findAll();

    @Transactional
    Assignment save(Assignment assignment);
}
