package com.example.springtraining.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
        @Transactional
        Lecturer findByLecturerId(Long lecturerId);

        @Transactional
        Lecturer save(Lecturer lecturer);

        List<Lecturer> findAll();

        @Query("SELECT l FROM Lecturer l JOIN l.assignments a WHERE a.assignmentId = :assignmentId")
        Lecturer findByAssignmentId(Long assignmentId);

        // Get all lecture have fit major
        @Query("SELECT lec FROM Lecturer lec JOIN lec.specializes s WHERE s.nameSpecialize = :label")
        Set<Lecturer> findByMajorFit(String label);

        // Get all lecture have fit timeLimit
        @Query("SELECT lec FROM Lecturer lec WHERE lec NOT IN " +
                        "(SELECT l FROM Lecturer l JOIN l.assignments a " +
                        "WHERE ((a.beginDate BETWEEN :beginDate AND :endDate OR " +
                        "a.endDate BETWEEN :beginDate AND :endDate) AND " +
                        "(:beginTime BETWEEN a.beginTime AND a.endTime OR " +
                        ":endTime BETWEEN a.beginTime AND a.endTime)) AND " +
                        "a.dayOfWeek = :dayOfWeek)")
        Set<Lecturer> findLecturerNotConfligTime(LocalDateTime beginDate,
                        LocalDateTime endDate,
                        LocalTime beginTime,
                        LocalTime endTime,
                        Integer dayOfWeek);

        @Query("SELECT lec FROM Lecturer lec WHERE NOT EXISTS " +
                        "(SELECT a FROM lec.assignments a WHERE " +
                        "(:beginDate BETWEEN a.beginDate AND a.endDate) AND " +
                        "(:endDate BETWEEN a.beginDate AND a.endDate) AND " +
                        "(a.beginDate BETWEEN :beginDate AND :endDate) AND " +
                        "(a.endDate BETWEEN :beginDate AND :endDate))")
        Set<Lecturer> findLecturerNotOverlapDate(LocalDateTime beginDate, LocalDateTime endDate);

        @Query("SELECT DISTINCT lec FROM Lecturer lec " +
                        "JOIN lec.assignments a " +
                        "WHERE EXISTS (" +
                        "SELECT a2 FROM lec.assignments a2 " +
                        "WHERE (a2.beginDate BETWEEN :beginDate AND :endDate " +
                        "OR a2.endDate BETWEEN :beginDate AND :endDate AND a2.dayOfWeek <> :dayOfWeek)" +
                        ")")
        Set<Lecturer> findLecturersWithOverlapDateAndNotDay(LocalDateTime beginDate, LocalDateTime endDate,
                        Integer dayOfWeek);

        // @Query("SELECT DISTINCT lec FROM Lecturer lec " +
        // "JOIN lec.assignments a " +
        // "WHERE EXISTS (" +
        // "SELECT a2 FROM lec.assignments a2 " +
        // "WHERE (a2.dayOfWeek = :dayOfWeek " +
        // "AND NOT ((a2.beginDate BETWEEN :beginDate AND :endDate) " +
        // "OR (a2.endDate BETWEEN :beginDate AND :endDate) ))" +
        // ")")
        @Query("SELECT DISTINCT lec FROM Lecturer lec " +
                        "JOIN lec.assignments a " +
                        "WHERE EXISTS (" +
                        "SELECT a2 FROM lec.assignments a2 " +
                        "WHERE (a2.beginDate BETWEEN :beginDate AND :endDate " +
                        "OR a2.endDate BETWEEN :beginDate AND :endDate) " +
                        "AND a2.dayOfWeek = :dayOfWeek " +
                        "AND (:beginTime NOT BETWEEN a2.beginTime AND a2.endTime " +
                        "OR :endTime NOT BETWEEN a2.beginTime AND a2.endTime)" + // Sử dụng OR thay vì AND ở đây
                        ")")
        Set<Lecturer> findLecturersWithOverlapDateAndDay(LocalDateTime beginDate, LocalDateTime endDate,
                        LocalTime beginTime, LocalTime endTime, Integer dayOfWeek);

        @Query("SELECT lec FROM Lecturer lec WHERE lec.limitHours > :currentHours")
        Set<Lecturer> findLecturerNotEnoughTime(Integer currentHours);

        @Query("SELECT lec FROM Lecturer lec WHERE lec.email =:email")
        List<Lecturer> findLectureByEmail(String email);
}
