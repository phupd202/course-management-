package com.example.springtraining.service;

import java.util.List;

import com.example.springtraining.entity.InterestedParty;

public interface InterestedPartyServices {
    List<InterestedParty> findByCourseId(Long courseId);
}
