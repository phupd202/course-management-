package com.example.springtraining.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springtraining.dto.LecturerDto;
import com.example.springtraining.entity.Lecturer;
import com.example.springtraining.mapper.LecturerDtoMapper;
import com.example.springtraining.repository.LecturerRepository;
import com.example.springtraining.service.LecturerService;

@Service
public class LecturerServiceImpl implements LecturerService {
    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public Lecturer findLecturerById(Long lecturerId) {
        if (lecturerId == null) {
            throw new NullPointerException("Null data");
        }
        return lecturerRepository.findByLecturerId(lecturerId);
    }

    @Override
    public List<LecturerDto> findAllLecturer() {
        return lecturerRepository.findAll()
                .stream()
                .map(LecturerDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Lecturer findByAssignmentId(Long assignmentId) {
        if (assignmentId == null) {
            throw new NullPointerException("assignment Id is null");
        }
        return lecturerRepository.findByAssignmentId(assignmentId);
    }

    @Override
    public Set<Lecturer> findByMajorFit(String label) {
        if (label == null) {
            throw new NullPointerException("Data input null");
        }
        return lecturerRepository.findByMajorFit(label);
    }

    @Override
    public Set<LecturerDto> findLecturerFreeAtTime(String beginDate, String endDate, String beginTime,
            String endTime, String label, Integer dayOfWeek) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime beginDateFormatted = LocalDateTime.parse(beginDate, formatter);
        LocalDateTime endDateFormatted = LocalDateTime.parse(endDate, formatter);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime beginTimeFormatted = LocalTime.parse(beginTime, formatterTime);
        LocalTime endTimeFormatted = LocalTime.parse(endTime, formatterTime);

        Set<Lecturer> lecturersNotOverlapDate = findLecturersNotOverlapDate(beginDateFormatted, endDateFormatted);
        Set<Lecturer> lecturersOverlapDateAndDay = findLecturersOverlapDateAndDay(beginDateFormatted, endDateFormatted,
                beginTimeFormatted, endTimeFormatted, dayOfWeek);
        Set<Lecturer> lecturersOverlapDateAndNotDay = findLecturersOverlapDateAndNotDay(beginDateFormatted,
                endDateFormatted, dayOfWeek);

        Set<Lecturer> lecturerFree = new HashSet<>();

        lecturerFree.addAll(lecturersNotOverlapDate);
        lecturerFree.addAll(lecturersOverlapDateAndDay);
        lecturerFree.addAll(lecturersOverlapDateAndNotDay);

        // lecturersNotOverlapDate.retainAll(lecturersOverlapDateAndNotDay);
        // lecturersNotOverlapDate.retainAll(lecturersOverlapDateAndDay);

        // lecturerFree
        return lecturersNotOverlapDate.stream()
                            .map(LecturerDtoMapper::mapToDto)
                            .collect(Collectors.toSet());
    }

    @Override
    public Set<Lecturer> findLecturersNotOverlapDate(LocalDateTime beginDate, LocalDateTime endDate) {
        if (beginDate == null || endDate == null) {
            throw new NullPointerException("Begin date or endDate null");
        }
        return lecturerRepository.findLecturerNotOverlapDate(beginDate, endDate);
    }

    @Override
    public Set<Lecturer> findLecturersOverlapDateAndNotDay(LocalDateTime beginDate, LocalDateTime endDate,
            Integer dayOfWeek) {
        if (beginDate == null || endDate == null) {
            throw new NullPointerException("Begin date or endDate null");
        }
        return lecturerRepository.findLecturersWithOverlapDateAndNotDay(beginDate, endDate, dayOfWeek);
    }

    @Override
    public Set<Lecturer> findLecturersOverlapDateAndDay(LocalDateTime beginDate, LocalDateTime endDate,
            LocalTime beginTime, LocalTime endTime, Integer dayOfWeek) {
        if (beginDate == null || endDate == null) {
            throw new NullPointerException("Begin date or endDate null");
        }
        return lecturerRepository.findLecturersWithOverlapDateAndDay(beginDate, endDate, beginTime, endTime, dayOfWeek);
    }

}
