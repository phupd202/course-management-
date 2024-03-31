package com.example.springtraining.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.Classroom;
import com.example.springtraining.repository.ClassroomRepository;
import com.example.springtraining.service.ClassroomService;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    @Transactional
    public Classroom save(Classroom classroom) {
        if (classroom == null) {
            throw new NullPointerException("Classroom null");
        }
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom findByCodeClassroom(String codeClassroom) {
        if (codeClassroom == null) {
            throw new NullPointerException("Code classroom null");
        }
        return classroomRepository.findByCodeClassroom(codeClassroom);
    }

    @Override
    public Classroom findByClassroomId(Long classroomId) {
        if(classroomId == null) {
            throw new NullPointerException("ClassroomId null");
        }
        return classroomRepository.findByClassroomId(classroomId);
    }
}
