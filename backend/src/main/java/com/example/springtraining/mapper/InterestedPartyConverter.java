package com.example.springtraining.mapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.example.springtraining.dto.InterestPartyDtoResponse;
import com.example.springtraining.entity.InterestedParty;

public class InterestedPartyConverter implements Converter<InterestedParty, InterestPartyDtoResponse> {
    @Override
    public InterestPartyDtoResponse convert(MappingContext<InterestedParty, InterestPartyDtoResponse> context) {
        InterestedParty source = context.getSource();
        InterestPartyDtoResponse destination = new InterestPartyDtoResponse();

        destination.setCourseId(source.getCourse().getCourseId());
        destination.setNameCourse(source.getCourse().getNameCourse());
        destination.setName(source.getName());
        destination.setEmail(source.getEmail());
        destination.setPhone(source.getPhone());
        destination.setAddress(source.getAddress());
        return destination;
    }
}
