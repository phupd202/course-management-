package com.example.springtraining.service;

import com.example.springtraining.entity.Classroom;

public interface ClassroomService {
    public Classroom save(Classroom classroom);

    public Classroom findByCodeClassroom(String codeClassroom);

    public Classroom findByClassroomId(Long classroomId);
}
