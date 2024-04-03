package com.example.springtraining.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.Register;

import java.util.List;

public interface RegisterRepository extends JpaRepository<Register, Long> {
    @Transactional
    Register save(Register register);

    @Transactional
    @Query("SELECT r FROM Register r JOIN r.classroom cls JOIN r.course c WHERE cls.classroomId = :classroomId AND c.courseId = :courseId")
    List<Register> findByClassroomId(Long classroomId, Long courseId);

    @Transactional
    Register findByRegisterId(Long registerId);
}
