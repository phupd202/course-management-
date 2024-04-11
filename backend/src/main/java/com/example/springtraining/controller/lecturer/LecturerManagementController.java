package com.example.springtraining.controller.lecturer;

import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.config.service.UserDetailsImpl;
import com.example.springtraining.dto.lecturer.AssignmentLecturerDto;
import com.example.springtraining.dto.lecturer.EnrollmentDto;
import com.example.springtraining.dto.lecturer.ScoreDto;
import com.example.springtraining.entity.Score;
import com.example.springtraining.repository.ScoreRepository;
import com.example.springtraining.service.AssignmentService;
import com.example.springtraining.service.EnrollmentService;
import com.example.springtraining.service.LecturerService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(LecturerManagementController.class);

    private final AssignmentService assignmentService;

    private final EnrollmentService enrollmentService;

    private final ScoreRepository scoreRepository;

    private final LecturerService lecturerService;

    public LecturerManagementController(AssignmentService assignmentService, EnrollmentService enrollmentService,
                                        ScoreRepository scoreRepository, LecturerService lecturerService) {
        this.assignmentService = assignmentService;
        this.enrollmentService = enrollmentService;
        this.scoreRepository = scoreRepository;
        this.lecturerService = lecturerService;   
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
    public ResponseEntity<List<EnrollmentDto>> getEnrollment(@RequestParam Long classroomId, @RequestParam Long subjectId) {
        if (classroomId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<EnrollmentDto> enrollmentDtos = enrollmentService.findAssignmentByClassroomIdAndSubjectId(classroomId, subjectId);
        Score score = scoreRepository.findByUserIdAndSubjectIdANDClassroomId(6L, 3L, 6L);
        logger.debug("Score: ", score);

        if (enrollmentDtos == null || enrollmentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(enrollmentDtos);
    }

    @PostMapping("/send-score")
    @Transactional
    public ResponseEntity<?> updateScore(@RequestBody ScoreDto scoreDto) {
        if(scoreDto == null) {
            throw new NullPointerException("ScoreDto is null");
        } else {
            try {
                lecturerService.saveScore(scoreDto);
                return ResponseEntity.ok(HttpStatus.OK);
            } catch(Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
    }
}