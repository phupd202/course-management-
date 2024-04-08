package com.example.springtraining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long>{
    @Transactional
    @Query("SELECT s FROM Score s JOIN s.user u WHERE u.userId = :userId")
    List<Score> findScoreByUserId(Long userId);

    @Transactional
    Score save(Score score);

    @Transactional
    @Query("SELECT s FROM Score s JOIN s.user u WHERE u.userId = :userId AND s.subject.subjectId = :subjectId")
    Score findByUserIdAndSubjectId(@Param("userId") Long userId, @Param("subjectId") Long subjectId);

    @Transactional
    @Query("SELECT s FROM Score s JOIN s.user u WHERE u.userId = :userId AND s.subject.subjectId = :subjectId AND s.classroom.classroomId = :classroomId")
    Score findByUserIdAndSubjectIdANDClassroomId(@Param("userId") Long userId, @Param("subjectId") Long subjectId, @Param("classroomId") Long classroomId);
}
