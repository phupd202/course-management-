package com.example.springtraining.mapper;

import com.example.springtraining.dto.SelectCourseDto;
import com.example.springtraining.entity.Course;

public class SelectCourseMapper {
    public static SelectCourseDto mapToDto(Course course) {
        SelectCourseDto selectCourseDto = new SelectCourseDto();
        selectCourseDto.setCourseId(course.getCourseId());
        selectCourseDto.setNameCourse(course.getNameCourse());
        return selectCourseDto;
    }
}
