package com.example.springtraining.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.Classroom;


@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    @Transactional
    Classroom save(Classroom classroom);

    @Query("SELECT classroom FROM Classroom classroom WHERE classroom.codeClassroom = :codeClassroom")
    Classroom findByCodeClassroom(@Param("codeClassroom") String codeClassroom);

    Classroom findByClassroomId(Long classroomId);
}
