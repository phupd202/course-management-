package com.example.springtraining.mapper;

import java.time.format.DateTimeFormatter;

import com.example.springtraining.dto.response.CourseResponse;
import com.example.springtraining.entity.Course;

public class CourseResponseMapper {
    public static CourseResponse mapToDto(Course course) {
        CourseResponse courseResponse = new CourseResponse();

        courseResponse.setCourseId(course.getCourseId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = course.getCreatedAt().format(formatter);
        courseResponse.setCreatedAt(formattedDateTime);

        courseResponse.setNameCourse(course.getNameCourse());
        courseResponse.setDescription(course.getDescription());
        courseResponse.setIsClosed(course.getIsClosed());
        courseResponse.setPrice(course.getPrice());

        Integer numClass = 0;
        if(course.getClassrooms() == null) {
            numClass = 0;
        } else {
            numClass = course.getClassrooms().size();
        }
        courseResponse.setNumClass(course.getClassrooms().size());
        courseResponse.setUrl(course.getUrl());
        return courseResponse;
    }
}
