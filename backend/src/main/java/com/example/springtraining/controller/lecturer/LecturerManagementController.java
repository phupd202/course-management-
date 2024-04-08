package com.example.springtraining.controller.lecturer;

import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.config.service.UserDetailsImpl;
import com.example.springtraining.dto.lecturer.AssignmentLecturerDto;
import com.example.springtraining.dto.lecturer.EnrollmentDto;
import com.example.springtraining.dto.lecturer.ScoreDto;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Score;
import com.example.springtraining.entity.Subject;
import com.example.springtraining.entity.User;
import com.example.springtraining.repository.ScoreRepository;
import com.example.springtraining.repository.UserRepository;
import com.example.springtraining.service.AssignmentService;
import com.example.springtraining.service.ClassroomService;
import com.example.springtraining.service.EnrollmentService;
import com.example.springtraining.service.SubjectService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

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

    private final ScoreRepository scoreRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ClassroomService classroomService;

    public LecturerManagementController(AssignmentService assignmentService, EnrollmentService enrollmentService,
            ScoreRepository scoreRepository) {
        this.assignmentService = assignmentService;
        this.enrollmentService = enrollmentService;
        this.scoreRepository = scoreRepository;
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
        System.out.println("score: " + score);

        if (enrollmentDtos == null || enrollmentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(enrollmentDtos);
    }

    @PostMapping("/send-score")
    @Transactional
    public ResponseEntity<?> updateScore(@RequestBody ScoreDto scoreDto) {
        if (scoreDto == null) {
            return ResponseEntity.badRequest().build();
        } else {
            Long subjectId = scoreDto.getSubjectId();
            Long classroomId = scoreDto.getClassroomId();

            if (subjectId == null || classroomId == null) {
                throw new NullPointerException("ClassroomId or subjectId is null");
            }

            Subject subject = subjectService.findSubjectById(subjectId);
            Classroom classroom = classroomService.findByClassroomId(classroomId);

            List<Long> enrollmentIds = scoreDto.getEnrollmentIds();
            List<Integer> scores = scoreDto.getScores();
            List<String> statues = scoreDto.getStatues();
            List<Long> userIds = enrollmentIds.stream()
                    .map(enrollmentId -> enrollmentService.findEnrollmentById(enrollmentId).getUser().getUserId())
                    .collect(Collectors.toList());

            for (int i = 0; i < enrollmentIds.size(); i++) {
                Long enrollmentId = enrollmentIds.get(i);
                Integer scoreValue = scores.get(i);
                String status = statues.get(i);

                Score existingScore = scoreRepository.findByUserIdAndSubjectIdANDClassroomId(userIds.get(i), subjectId, classroomId);
                if (existingScore != null) {
                    // Nếu đã tồn tại, cập nhật điểm
                    existingScore.setScore(scoreValue);
                    existingScore.setStatus(status);
                    scoreRepository.save(existingScore);
                } else {
                    // Nếu không tồn tại, tạo mới bản ghi điểm
                    Score newScore = new Score();
                    newScore.setScore(scoreValue);
                    newScore.setStatus(status);
                    newScore.setSubject(subject);
                    newScore.setClassroom(classroom);
                    User user = enrollmentService.findEnrollmentById(enrollmentId).getUser();
                    newScore.setUser(user);
                    scoreRepository.save(newScore);
                }
            }
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    // public ResponseEntity<?> putMethodName(@RequestParam(name = "classroomId")
    // Long classroomId,
    // @RequestParam(name = "subjectId") Long subjectId,
    // @RequestBody List<ScoreDto> scoreDtos) {

    // if(subjectId == null || classroomId == null || scoreDtos == null) {
    // return ResponseEntity.badRequest().build();
    // }
    // Subject subject = subjectService.findSubjectById(subjectId);

    // Classroom classroom = classroomService.findByClassroomId(classroomId);
    // Hibernate.initialize(classroom.getEnrollments());

    // for (ScoreDto scoreDto : scoreDtos) {
    // Long enrollmentId = scoreDto.getEnrollmentId();
    // Enrollment enrollment = enrollmentService.findEnrollmentById(enrollmentId);

    // classroom.addEnrollment(enrollment);
    // enrollment.addSubject(subject);

    // // enrollment.setScore(scoreDto.getScore());
    // // enrollment.setStatus(scoreDto.getStatus());

    // enrollmentService.save(enrollment);
    // }
    // return ResponseEntity.ok(HttpStatus.OK);
    // }
}
