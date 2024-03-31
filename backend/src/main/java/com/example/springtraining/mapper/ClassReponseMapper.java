package com.example.springtraining.mapper;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.example.springtraining.dto.response.ClassroomResponse;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Subject;

public class ClassReponseMapper {
    public static ClassroomResponse mapToDto(Classroom classroom) {
        ClassroomResponse classroomResponse = new ClassroomResponse();

        classroomResponse.setClassroomId(classroom.getClassroomId());
        classroomResponse.setCodeClassroom(classroom.getCodeClassroom());

        Integer sumEstimate = classroom.getCourse()
                                    .getSubjects()
                                    .stream()
                                    .mapToInt(Subject :: getEstimate)
                                    .filter(Objects::nonNull)
                                    .sum();
                                    

        classroomResponse.setSumPeriod(sumEstimate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedBeginDate = classroom.getBeginDate().format(formatter);
        classroomResponse.setBeginDate(formattedBeginDate);

        String formatterEndDate = classroom.getEndDate().format(formatter);
        classroomResponse.setEndDate(formatterEndDate);

        classroomResponse.setSumStudent(classroom.getEnrollments().size());
        
        return classroomResponse;
    }
}
