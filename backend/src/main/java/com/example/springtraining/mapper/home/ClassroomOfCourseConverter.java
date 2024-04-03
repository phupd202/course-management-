package com.example.springtraining.mapper.home;

import java.time.format.DateTimeFormatter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.example.springtraining.dto.response.ClassroomOfCourse;
import com.example.springtraining.entity.Classroom;

public class ClassroomOfCourseConverter implements Converter<Classroom, ClassroomOfCourse>{

    @Override
    public ClassroomOfCourse convert(MappingContext<Classroom, ClassroomOfCourse> context) {
        Classroom source = context.getSource();

        ClassroomOfCourse classroomOfCourse = new ClassroomOfCourse();
        classroomOfCourse.setClassroomId(source.getClassroomId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        classroomOfCourse.setEndDate(source.getEndDate().format(formatter));
        classroomOfCourse.setBeginDate(source.getBeginDate().format(formatter));
        return classroomOfCourse;
    }
    
}
