package com.example.springtraining.mapper;

import java.time.format.DateTimeFormatter;

import com.example.springtraining.dto.response.StudentOfClassResponse;
import com.example.springtraining.entity.Enrollment;

public class EnrollmentMapper {
    public static StudentOfClassResponse mapToDto(Enrollment enrollment) {
        if(enrollment == null) {
            throw new NullPointerException("enrollment null!");
        }
        StudentOfClassResponse studentOfClassResponse = new StudentOfClassResponse();

        studentOfClassResponse.setUserId(enrollment.getUser().getUserId());
        studentOfClassResponse.setEmail(enrollment.getUser().getEmail());
        studentOfClassResponse.setAddress(enrollment.getUser().getAddress());
        studentOfClassResponse.setPhone(enrollment.getUser().getPhone());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatedEnrollmentDate = enrollment.getEnrollmentDay().format(formatter);
        studentOfClassResponse.setEnrollmentDay(formatedEnrollmentDate);
        return studentOfClassResponse;
    } 
}
