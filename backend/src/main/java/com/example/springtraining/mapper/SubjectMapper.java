package com.example.springtraining.mapper;

import java.time.format.DateTimeFormatter;

import com.example.springtraining.dto.SubjectDto;
import com.example.springtraining.entity.Assignment;
import com.example.springtraining.entity.Lecturer;
import com.example.springtraining.entity.Subject;

public class SubjectMapper {
    public static SubjectDto mapToDto(Subject subject, Assignment assignment, Lecturer lecturer) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setEstimate(subject.getEstimate());
        subjectDto.setNameSubject(subject.getNameSubject());
        subjectDto.setSubjectId(subject.getSubjectId());
    
        if (assignment != null) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            subjectDto.setBeginDate(assignment.getBeginDate() != null ? assignment.getBeginDate().format(dateFormatter) : null);
            subjectDto.setEndDate(assignment.getEndDate() != null ? assignment.getEndDate().format(dateFormatter) : null);
    
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            subjectDto.setBeginTime(assignment.getBeginTime() != null ? assignment.getBeginTime().format(timeFormatter) : null);
            subjectDto.setEndTime(assignment.getEndTime() != null ? assignment.getEndTime().format(timeFormatter) : null);
    
            subjectDto.setIsClosed(assignment.getIsClosed());
            subjectDto.setDayOfWeek(assignment.getDayOfWeek() != null ? assignment.getDayOfWeek() : null);
        }
    
        if (lecturer != null) {
            subjectDto.setLecturerId(lecturer.getLecturerId());
            subjectDto.setNameLecturer(lecturer.getNameLecture());
        }
    
        return subjectDto;
    }
    
}
