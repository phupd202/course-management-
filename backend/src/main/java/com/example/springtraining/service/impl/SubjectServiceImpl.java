package com.example.springtraining.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.Subject;
import com.example.springtraining.repository.CourseRepository;
import com.example.springtraining.repository.SubjectRepository;
import com.example.springtraining.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> findSubjectsByCourseId(Long courseId) {
        List<Subject> subjects = courseRepository.findSubjectsByCourseId(courseId);
        if (subjects == null || subjects.isEmpty()) {
            throw new NullPointerException("Dữ liệu trong db trống");
        }
        return subjects;
    }

    @Override
    @Transactional
    public Subject saveSubject(Subject subject) {
        if (subject == null) {
            throw new NullPointerException("Data null");
        }
        return subjectRepository.save(subject);
    }

    @Override
    public void softDelete(Long subjectId, Boolean isDeleted) {
       Subject subject = subjectRepository.findBySubjectId(subjectId);

       if(subject == null) {
            return; 
       }
       subject.setIsDeleted(isDeleted);
       subjectRepository.save(subject);
    }

    @Override
    public Subject findSubjectById(Long subjectId) {
        return subjectRepository.findBySubjectId(subjectId);
    }
}
