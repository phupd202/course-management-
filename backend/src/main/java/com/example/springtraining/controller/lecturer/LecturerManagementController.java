package com.example.springtraining.controller.lecturer;

import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.config.service.UserDetailsImpl;
import com.example.springtraining.dto.lecturer.AssignmentLecturerDto;
import com.example.springtraining.dto.lecturer.EnrollmentDto;
import com.example.springtraining.dto.lecturer.ScoreDto;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Enrollment;
import com.example.springtraining.entity.Subject;
import com.example.springtraining.service.AssignmentService;
import com.example.springtraining.service.ClassroomService;
import com.example.springtraining.service.EnrollmentService;
import com.example.springtraining.service.SubjectService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/course-management/lecturer")
public class LecturerManagementController {
    private final AssignmentService assignmentService;

    private final EnrollmentService enrollmentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ClassroomService classroomService;

    public LecturerManagementController(AssignmentService assignmentService, EnrollmentService enrollmentService) {
        this.assignmentService = assignmentService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/get-all-assignment")
    public ResponseEntity<List<AssignmentLecturerDto>> getAllAsssignment() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        List<AssignmentLecturerDto> assignmentLecturerDtos = assignmentService.findAssignmentOfEmailLecturer(email);

        if (assignmentLecturerDtos == null || assignmentLecturerDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(assignmentLecturerDtos);
        }
    }

    @GetMapping("/get-enrollment")
    public ResponseEntity<List<EnrollmentDto>> getMethodName(@RequestParam Long classroomId) {
        if(classroomId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<EnrollmentDto> enrollmentDtos = enrollmentService.findAssignmentByClassroomId(classroomId);

        if(enrollmentDtos == null || enrollmentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(enrollmentDtos);
    }

    @PostMapping("/send-score")
    @Transactional
    public ResponseEntity<?> putMethodName(@RequestParam(name = "classroomId") Long classroomId, 
                                            @RequestParam(name = "subjectId") Long subjectId,
                                            @RequestBody List<ScoreDto> scoreDtos) {

        if(subjectId == null || classroomId == null || scoreDtos == null) {
            return ResponseEntity.badRequest().build();
        }
        Subject subject = subjectService.findSubjectById(subjectId);
        
        Classroom classroom = classroomService.findByClassroomId(classroomId);
        Hibernate.initialize(classroom.getEnrollments());

        for (ScoreDto scoreDto : scoreDtos) {
            Long enrollmentId = scoreDto.getEnrollmentId();
            Enrollment enrollment = enrollmentService.findEnrollmentById(enrollmentId);

            classroom.addEnrollment(enrollment);
            enrollment.addSubject(subject);

            enrollment.setScore(scoreDto.getScore());
            enrollment.setStatus(scoreDto.getStatus());

            enrollmentService.save(enrollment);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
