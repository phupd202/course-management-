package com.example.springtraining.controller.user;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.config.service.UserDetailsImpl;
import com.example.springtraining.dto.response.ClassroomOfCourse;
import com.example.springtraining.dto.user.ScoreDto;
import com.example.springtraining.dto.user.SubjectOfCourse;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Course;
import com.example.springtraining.entity.Score;
import com.example.springtraining.entity.Subject;
import com.example.springtraining.entity.User;
import com.example.springtraining.repository.CourseRepository;
import com.example.springtraining.repository.ScoreRepository;
import com.example.springtraining.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/course-management")
public class UserMyCourse {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping("/user/get-my-course")
    public ResponseEntity<List<ClassroomOfCourse>> getMyCourse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();

        try {
            User user = userRepository.findByEmailUser(email);
        
            List<Classroom> classrooms = user.getEnrollments().stream()
                                .map(enrollment -> enrollment.getClassroom())
                                .collect(Collectors.toList());
            List<ClassroomOfCourse> classroomOfCourses = classrooms.stream()
                                                                    .map(classroom -> modelMapper.map(classroom, ClassroomOfCourse.class))
                                                                    .collect(Collectors.toList());
            return ResponseEntity.ok(classroomOfCourses);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/subject-of-class/{courseId}")
    public ResponseEntity<List<SubjectOfCourse>> getSubjectOfClass(@PathVariable(name = "courseId") Long courseId) {
        Course course = courseRepository.findByCourseId(courseId);

        try {
            List<Subject> subjects = course.getSubjects();
            List<SubjectOfCourse> subjectOfCourses =   subjects.stream()
                                                            .map(subject -> modelMapper.map(subject, SubjectOfCourse.class))
                                                            .collect(Collectors.toList());
            return ResponseEntity.ok(subjectOfCourses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/get-score/{classroomId}")
    public ResponseEntity<List<ScoreDto>> getScore(@PathVariable(name = "classroomId") Long classroomId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String email = userDetails.getEmail();

            // Find user by email
            User user= userRepository.findByEmailUser(email);
            
            // Find scores by user id and classroom id
            List<Score> scores = scoreRepository.findScoreByUserId(user.getUserId());
            List<Score> filteredScores = scores.stream()
                    .filter(score -> score.getClassroom().getClassroomId().equals(classroomId))
                    .collect(Collectors.toList());

            // Map Score entities to ScoreDto objects
            List<ScoreDto> scoreDtos = filteredScores.stream()
                    .map(score -> modelMapper.map(score, ScoreDto.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(scoreDtos);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
