package com.example.springtraining.controller.lecturer;

import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.config.service.UserDetailsImpl;
import com.example.springtraining.dto.lecturer.AssignmentLecturerDto;
import com.example.springtraining.dto.lecturer.EnrollmentDto;
import com.example.springtraining.dto.lecturer.PersonaleEventDto;
import com.example.springtraining.dto.lecturer.QuestionDto;
import com.example.springtraining.dto.lecturer.ResponsePersonalEvent;
import com.example.springtraining.dto.lecturer.ScoreDto;
import com.example.springtraining.entity.Lecturer;
import com.example.springtraining.entity.PersonalEvent;
import com.example.springtraining.entity.Question;
import com.example.springtraining.entity.Score;
import com.example.springtraining.repository.LecturerRepository;
import com.example.springtraining.repository.PersonalEventRepository;
import com.example.springtraining.repository.ScoreRepository;
import com.example.springtraining.service.AssignmentService;
import com.example.springtraining.service.EnrollmentService;
import com.example.springtraining.service.LecturerService;
import com.example.springtraining.service.PersonalEventService;
import com.example.springtraining.service.QuestionService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final PersonalEventService personalEventService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private LecturerRepository lecturerRepository;

    public LecturerManagementController(AssignmentService assignmentService, EnrollmentService enrollmentService,
                                        ScoreRepository scoreRepository, LecturerService lecturerService,
                                        PersonalEventService personalEventService) {
        this.assignmentService = assignmentService;
        this.enrollmentService = enrollmentService;
        this.scoreRepository = scoreRepository;
        this.lecturerService = lecturerService;   
        this.personalEventService = personalEventService;
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

    @PostMapping("/save-event")
    public ResponseEntity<?> saveEvent(@RequestBody PersonaleEventDto personaleEventDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String emailLecturer = userDetails.getUsername();
        try {
            personalEventService.saveEvent(personaleEventDto, emailLecturer);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get-personal-event")
    public ResponseEntity<List<ResponsePersonalEvent>> getPersonalEvent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();

        List<ResponsePersonalEvent> responsePersonalEvent =  personalEventService.getPersonalEvent(email);
        
        return ResponseEntity.ok(responsePersonalEvent);
    }

    @GetMapping("/list-question/{subjectId}")
    public ResponseEntity<List<QuestionDto>> getQuestionOfSubject(@PathVariable(name = "subjectId") Long subjectId) {
        List<QuestionDto> questionDtos = questionService.getQuestionOfSubject(subjectId);
        return ResponseEntity.ok(questionDtos);
    }

    @GetMapping("/fetch-infor-lecturer")
    public ResponseEntity<Long> fetchInforLecturer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
    
        Lecturer lecturer = lecturerRepository.findLecturerByEmail(email);

        return ResponseEntity.ok(lecturer.getLecturerId());
    }
}