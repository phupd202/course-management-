package com.example.springtraining.mapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.example.springtraining.dto.RegisterQueueResponse;
import com.example.springtraining.entity.Register;

public class RegisterMapper implements Converter<Register, RegisterQueueResponse>{

    @Override
    public RegisterQueueResponse convert(MappingContext<Register, RegisterQueueResponse> context) {
        Register source = context.getSource();

        RegisterQueueResponse registerQueueResponse = new RegisterQueueResponse();
        registerQueueResponse.setAddress(source.getAddress());
        registerQueueResponse.setClassroomId(source.getClassroom().getClassroomId());
        registerQueueResponse.setCourseId(source.getCourse().getCourseId());
        registerQueueResponse.setEmail(source.getEmail());
        registerQueueResponse.setName(source.getName());
        registerQueueResponse.setPhone(source.getPhone());
        registerQueueResponse.setRegisterId(source.getRegisterId());
        registerQueueResponse.setAddedClassroom(source.getAddedClassroom());
        registerQueueResponse.setCreatedAccount(source.getCreatedAccount());
        return registerQueueResponse;
    } 
}
