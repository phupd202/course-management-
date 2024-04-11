package com.example.springtraining.mapper.lecturer;

import java.time.format.DateTimeFormatter;

import com.example.springtraining.dto.lecturer.EnrollmentDto;
import com.example.springtraining.entity.Enrollment;

public class EnrollmentMapper {
    final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EnrollmentDto mapToDto(Enrollment enrollment) {
        EnrollmentDto enrollmentDto = new EnrollmentDto();

        enrollmentDto.setEnrollmentId(enrollment.getEnrollmentId());
        enrollmentDto.setName(enrollment.getUser().getName());
        enrollmentDto.setAddress(enrollment.getUser().getAddress());

        enrollmentDto.setEmail(enrollment.getUser().getEmail());
        enrollmentDto.setPhone((enrollment.getUser().getPhone()));
        return enrollmentDto;
    }
}
