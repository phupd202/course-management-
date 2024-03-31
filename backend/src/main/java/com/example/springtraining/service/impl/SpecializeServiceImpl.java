package com.example.springtraining.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springtraining.entity.Specialize;
import com.example.springtraining.repository.SpecializeRepository;
import com.example.springtraining.service.SpecializeService;

@Service
public class SpecializeServiceImpl implements SpecializeService {
    private final SpecializeRepository specializeRepository;

    public SpecializeServiceImpl(SpecializeRepository specializeRepository)  {
        this.specializeRepository = specializeRepository;
    }

    @Override
    public List<Specialize> findAllSpecialize() {
        return specializeRepository.findAll();
    }
    
}
