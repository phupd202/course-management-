package com.example.springtraining.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import com.example.springtraining.dto.CheckAssignmentDto;
import com.example.springtraining.dto.LecturerDto;
import com.example.springtraining.dto.lecturer.ScoreDto;
import com.example.springtraining.entity.Lecturer;

public interface LecturerService {
    Lecturer findLecturerById(Long lecturerId);

    List<LecturerDto> findAllLecturer();

    Lecturer findByAssignmentId(Long assignmentId);

    Set<Lecturer> findByMajorFit(String label);

    Set<Lecturer> findLecturersNotOverlapDate(LocalDateTime beginDate, LocalDateTime endDate);

    Set<Lecturer> findLecturersOverlapDateAndNotDay(LocalDateTime beginDate, LocalDateTime endDate, Integer dayOfWeek);

    Set<Lecturer> findLecturersOverlapDateAndDay(LocalDateTime beginDate, LocalDateTime endDate, LocalTime beginTime, LocalTime endTime, Integer dayOfWeek);

    List<LecturerDto> findLecturerFreeAtTime(CheckAssignmentDto checkAssignmentDto);

    void saveScore(ScoreDto scoreDto);
}
