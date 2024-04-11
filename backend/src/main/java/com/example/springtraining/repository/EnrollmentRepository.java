package com.example.springtraining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e FROM Enrollment e JOIN e.classroom c WHERE c.classroomId = :classroomId")
    List<Enrollment> findEnrollmentByClassroomId(Long classroomId);

    @Transactional
    Enrollment findByEnrollmentId(Long enrollmentId);

    Enrollment save(Enrollment enrollment);
}
