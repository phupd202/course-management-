package com.example.springtraining.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springtraining.entity.InterestedParty;
import com.example.springtraining.repository.InterestedPartyRepository;
import com.example.springtraining.service.InterestedPartyServices;

@Service
public class InterestedPartyServiceImpl implements InterestedPartyServices {
    @Autowired
    private InterestedPartyRepository interestedPartyRepository;

    @Override
    public List<InterestedParty> findByCourseId(Long courseId) {
       if(courseId == null) {
            throw new NullPointerException("Courseid is null");
       }
       return interestedPartyRepository.findByCourseId(courseId);
    } 
}
