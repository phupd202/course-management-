package com.example.springtraining.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springtraining.dto.CheckAssignmentDto;
import com.example.springtraining.dto.LecturerDto;
import com.example.springtraining.dto.lecturer.ScoreDto;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Lecturer;
import com.example.springtraining.entity.Score;
import com.example.springtraining.entity.Subject;
import com.example.springtraining.entity.User;
import com.example.springtraining.mapper.LecturerDtoMapper;
import com.example.springtraining.repository.AssignmentRepository;
import com.example.springtraining.repository.ClassroomRepository;
import com.example.springtraining.repository.EnrollmentRepository;
import com.example.springtraining.repository.LecturerRepository;
import com.example.springtraining.repository.ScoreRepository;
import com.example.springtraining.repository.SubjectRepository;
import com.example.springtraining.service.LecturerService;

@Service
public class LecturerServiceImpl implements LecturerService {
    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

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
    public List<LecturerDto> findLecturerFreeAtTime(CheckAssignmentDto checkAssignmentDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        String beginDate = checkAssignmentDto.getBeginDate();
        String endDate = checkAssignmentDto.getEndDate();

        String beginTime = checkAssignmentDto.getBeginTime();
        String endTime = checkAssignmentDto.getEndTime();

        Integer dayOfWeek = checkAssignmentDto.getDayOfWeek();

        // convert string to date, time
        LocalDateTime beginDateFormatted = LocalDateTime.parse(beginDate, formatter);
        LocalDateTime endDateFormatted = LocalDateTime.parse(endDate, formatter);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime beginTimeFormatted = LocalTime.parse(beginTime, formatterTime);
        LocalTime endTimeFormatted = LocalTime.parse(endTime, formatterTime);


        // find free lecturer
        Set<Lecturer> lecturersNotOverlapDate = findLecturersNotOverlapDate(beginDateFormatted, endDateFormatted);
        Set<Lecturer> lecturersOverlapDateAndDay = findLecturersOverlapDateAndDay(beginDateFormatted, endDateFormatted,
                beginTimeFormatted, endTimeFormatted, dayOfWeek);
        Set<Lecturer> lecturersOverlapDateAndNotDay = findLecturersOverlapDateAndNotDay(beginDateFormatted,
                endDateFormatted, dayOfWeek);

        List<Lecturer> lecturerFree = new ArrayList<Lecturer>();

        List<Lecturer> allLecturers = lecturerRepository.findAll();

        List<Lecturer> assignedLecturer = assignmentRepository.findAll().stream()   
                                                                        .map(assignment -> assignment.getLecturer())
                                                                        .collect(Collectors.toList());
        // lecturer is free if lecturer belong to one of three sets.
        allLecturers.stream()
                            .filter(lecturer -> lecturersNotOverlapDate.contains(lecturer) ||
                               lecturersOverlapDateAndDay.contains(lecturer) ||
                               lecturersOverlapDateAndNotDay.contains(lecturer) || 
                               !assignedLecturer.contains(lecturer))
                            .forEach(lecturerFree::add);

        return allLecturers.stream()
                            .map(LecturerDtoMapper::mapToDto)
                            .collect(Collectors.toList());

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

    @Override
    public void saveScore(ScoreDto scoreDto) {
        if (scoreDto == null) {
            throw new NullPointerException("ScoreDto is null!!");
        } else {
            Long subjectId = scoreDto.getSubjectId();
            Long classroomId = scoreDto.getClassroomId();

            if (subjectId == null || classroomId == null) {
                throw new NullPointerException("ClassroomId or subjectId is null");
            }

            Subject subject = subjectRepository.findBySubjectId(subjectId);
            Classroom classroom = classroomRepository.findByClassroomId(classroomId);

            List<Long> enrollmentIds = scoreDto.getEnrollmentIds();
            List<Integer> scores = scoreDto.getScores();
            List<String> statues = scoreDto.getStatues();
            List<Long> userIds = enrollmentIds.stream()
                .map(enrollmentId -> enrollmentRepository.findByEnrollmentId(enrollmentId).getUser().getUserId())
                .collect(Collectors.toList());

            for (int i = 0; i < enrollmentIds.size(); i++) {
                Long enrollmentId = enrollmentIds.get(i);
                Integer scoreValue = scores.get(i);
                String status = statues.get(i);

                Score existingScore = scoreRepository.findByUserIdAndSubjectIdANDClassroomId(userIds.get(i), subjectId, classroomId);
                if (existingScore != null) {
                    // Nếu đã tồn tại, cập nhật điểm
                    existingScore.setScore(scoreValue);
                    existingScore.setStatus(status);
                    scoreRepository.save(existingScore);
                } else {
                    // Nếu không tồn tại, tạo mới bản ghi điểm
                    Score newScore = new Score();
                    newScore.setScore(scoreValue);
                    newScore.setStatus(status);
                    newScore.setSubject(subject);
                    newScore.setClassroom(classroom);
                    User user = enrollmentRepository.findByEnrollmentId(enrollmentId).getUser();
                    newScore.setUser(user);
                    scoreRepository.save(newScore);
                }
            }
        }
    }

}
