package com.example.springtraining.mapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.example.springtraining.dto.user.SubjectOfCourse;
import com.example.springtraining.entity.Subject;

public class SubjectConverter implements Converter<Subject, SubjectOfCourse>{

    @Override
    public SubjectOfCourse convert(MappingContext<Subject, SubjectOfCourse> context) {
        Subject source = context.getSource();

        SubjectOfCourse subjectOfCourse = new SubjectOfCourse();

        subjectOfCourse.setCourseId(source.getCourse().getCourseId());
        subjectOfCourse.setSubjectId(source.getSubjectId());
        subjectOfCourse.setContent(source.getContent());
        subjectOfCourse.setLabel(source.getLabel());
        subjectOfCourse.setNameSubject(source.getNameSubject());
        return subjectOfCourse;
    }
    
}
