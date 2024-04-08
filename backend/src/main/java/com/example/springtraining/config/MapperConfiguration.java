package com.example.springtraining.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.springtraining.mapper.InterestedPartyConverter;
import com.example.springtraining.mapper.RegisterMapper;
import com.example.springtraining.mapper.home.ClassroomOfCourseConverter;
import com.example.springtraining.mapper.home.CourseResponseConverter;
import com.example.springtraining.mapper.user.ScoreMapper;

@Configuration
public class MapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new InterestedPartyConverter());
        modelMapper.addConverter(new CourseResponseConverter());
        modelMapper.addConverter(new ClassroomOfCourseConverter());
        modelMapper.addConverter(new RegisterMapper());
        modelMapper.addConverter(new ScoreMapper());
        return modelMapper;
    }
}
