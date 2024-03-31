package com.example.springtraining.mapper;

import java.time.format.DateTimeFormatter;

import com.example.springtraining.dto.lecturer.AssignmentLecturerDto;
import com.example.springtraining.entity.Assignment;

public class LectureAssignmentMapper {
    final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static AssignmentLecturerDto mapToDto(Assignment assignment) {
        AssignmentLecturerDto assigmentDto = new AssignmentLecturerDto();

        assigmentDto.setNameSubject(assignment.getSubject().getNameSubject());
        assigmentDto.setCodeClassroom(assignment.getClassroom().getCodeClassroom());

        String beginDateFormatted = assignment.getBeginDate().format(DATE_TIME_FORMATTER);
        assigmentDto.setBeginDate(beginDateFormatted);

        assigmentDto.setSubjectId(assignment.getSubject().getSubjectId());

        String endDateFormated = assignment.getEndDate().format(DATE_TIME_FORMATTER);
        assigmentDto.setEndDate(endDateFormated);

        String beginTimeFormatted = assignment.getBeginTime().format(TIME_FORMATTER);
        assigmentDto.setBeginTime(beginTimeFormatted);

        String endTimeFormatted = assignment.getEndTime().format(TIME_FORMATTER);
        assigmentDto.setEndTime(endTimeFormatted);

        assigmentDto.setClassroomId(assignment.getClassroom().getClassroomId());

        assigmentDto.setDayOfWeek(assignment.getDayOfWeek());
        return assigmentDto;
    }
}
