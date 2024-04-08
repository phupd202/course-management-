package com.example.springtraining.mapper.home;

import java.time.format.DateTimeFormatter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import com.example.springtraining.dto.response.CourseResponse;
import com.example.springtraining.entity.Course;

public class CourseResponseConverter implements Converter<Course, CourseResponse>{

    @Override
    public CourseResponse convert(MappingContext<Course, CourseResponse> context) {
        Course source = context.getSource();

        CourseResponse courseResponse = new CourseResponse();

        courseResponse.setCourseId(source.getCourseId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = source.getCreatedAt().format(formatter);
        courseResponse.setCreatedAt(formattedDateTime);

        courseResponse.setNameCourse(source.getNameCourse());
        courseResponse.setDescription(source.getDescription());
        courseResponse.setIsClosed(source.getIsClosed());
        courseResponse.setPrice(source.getPrice());

        Integer numClass = 0;
        if(source.getClassrooms() == null) {
            numClass = 0;
        } else {
            numClass = source.getClassrooms().size();
        }
        courseResponse.setNumClass(source.getClassrooms().size());
        courseResponse.setUrl(source.getUrl());
        return courseResponse;
    }
    
}
