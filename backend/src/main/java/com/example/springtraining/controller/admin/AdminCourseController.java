package com.example.springtraining.controller.admin;

import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.dto.InterestPartyDtoResponse;
import com.example.springtraining.dto.LecturerDto;
import com.example.springtraining.dto.admin.CourseResponse;
import com.example.springtraining.dto.request.CourseBodyRequest;
import com.example.springtraining.dto.request.CourseRequest;
import com.example.springtraining.dto.request.EditCourse;
import com.example.springtraining.dto.request.SubjectRequest;
import com.example.springtraining.dto.response.ManageSubjectReponse;
import com.example.springtraining.entity.Course;
import com.example.springtraining.entity.InterestedParty;
import com.example.springtraining.entity.Subject;
import com.example.springtraining.repository.CourseRepository;
import com.example.springtraining.repository.InterestedPartyRepository;
import com.example.springtraining.service.CourseService;
import com.example.springtraining.service.LecturerService;
import com.example.springtraining.service.SpecializeService;
import com.example.springtraining.service.SubjectService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/course-management/admin")
@CrossOrigin(origins = "http://localhost:8081")
public class AdminCourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private LecturerService lecturerService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SpecializeService specializeService;

    @Autowired
    private InterestedPartyRepository interestedPartyRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdminCourseController.class);

    @PostMapping("/courses")
    public ResponseEntity<?> addCourse(@RequestBody CourseRequest courseRequest) {
        if (courseRequest == null) {
            return ResponseEntity.badRequest().body("Dữ liệu nhận được null");
        } else {
            logger.debug(courseRequest.toString());
            try {
                Course newCourse = new Course();

                newCourse.setNameCourse(courseRequest.getNameCourse());
                newCourse.setDescription(courseRequest.getDescription());
                newCourse.setUrl(courseRequest.getUrl());
                newCourse.setIsClosed(true);
                newCourse.setPrice(courseRequest.getPrice());

                courseService.save(newCourse);
                return ResponseEntity.ok("Update database thành công");
            } catch (Exception e) {
                e.printStackTrace();
                return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/courses")
    public Page<CourseResponse> getAllCourse(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size

    ) {
        Pageable pageable = PageRequest.of(page, size);
        return courseService.findAllCourse(pageable);
    }

    @GetMapping("/search-course")
    public ResponseEntity<List<CourseResponse>> getCourseByKeyword(@RequestParam(name = "keyword") String keyword) {
        List<CourseResponse> course = courseService.findCourseByKeyword(keyword);
        return ResponseEntity.ok(course);
    }

    // Đóng khoá học
    @PutMapping("/courses")
    public ResponseEntity<?> closeCourse(@RequestBody CourseBodyRequest courseBodyRequest) {

        if (courseBodyRequest.getIsClosed() == null || courseBodyRequest.getCourseId() == null) {
            return ResponseEntity.badRequest().body("Các tham số không được null!");
        }

        Course course = courseService.findByCourseId(courseBodyRequest.getCourseId());
        if(courseBodyRequest.getIsClosed() == true) {
            course.setIsClosed(false);
            courseService.save(course);
        } else {
            course.setIsClosed(true);
            courseService.save(course);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-course")
    public ResponseEntity<?> updateCourse(@RequestBody CourseResponse courseResponse) {
        try {
            courseService.updateCourse(courseResponse);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } 


    }

    @GetMapping("/detail-course/{courseId}")
    public ResponseEntity<List<ManageSubjectReponse>> getCourseDetails(@PathVariable(name = "courseId") Long courseId) {
        if(courseId == null) {
            logger.debug("CourseId is null!");
            return ResponseEntity.badRequest().build();
        }
    
        Course course = courseService.findByCourseId(courseId);
        if(course == null) {
            logger.debug("Course not found for id: " + courseId);
            return ResponseEntity.notFound().build();
        }
    
        List<Subject> subjects = course.getSubjects();
        if(subjects == null || subjects.isEmpty()) {
            logger.debug("No subjects found for course with id: " + courseId);
            return ResponseEntity.noContent().build();
        }
    
        Integer sumEstimate = subjects.stream()
                                     .mapToInt(Subject :: getEstimate)
                                     .sum();

        Integer sumSubject = subjects.size();
    
        List<ManageSubjectReponse> manageSubjectReponses = new ArrayList<ManageSubjectReponse>();
        for (Subject subject : subjects) {
            ManageSubjectReponse manageSubjectReponse = new ManageSubjectReponse();
            manageSubjectReponse.setSubjectId(subject.getSubjectId());
            manageSubjectReponse.setEstimate(subject.getEstimate());
            manageSubjectReponse.setNameSubject(subject.getNameSubject());
            manageSubjectReponse.setSumEstimate(sumEstimate);
            manageSubjectReponse.setSumSubject(sumSubject);
            manageSubjectReponse.setContent(subject.getContent());
            manageSubjectReponses.add(manageSubjectReponse);
        }
        return ResponseEntity.ok(manageSubjectReponses);
    }

    @PostMapping("/add-subject/{courseId}")
    @Transactional
    public ResponseEntity<?> addSubject(@PathVariable(name = "courseId") Long courseId,
                                        @RequestBody SubjectRequest subjectRequest) {
        if(courseId == null || subjectRequest == null) {
            throw new NullPointerException("CourseId null");
        }

        Course course = courseService.findByCourseId(courseId);

        if(course == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Subject newSubject = new Subject();
            newSubject.setContent(subjectRequest.getContent());
            newSubject.setNameSubject(subjectRequest.getNameSubject());
            newSubject.setEstimate(subjectRequest.getEstimate());
            newSubject.setLabel(subjectRequest.getLabel());
            
            newSubject.setCourse(course);
            newSubject.setIsDeleted(false);
            
            subjectService.saveSubject(newSubject);
            courseService.save(course);
            return ResponseEntity.ok().build();
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/get-lecturers")
    public ResponseEntity<List<LecturerDto>> getAllLecturer() {
        List<LecturerDto> response = lecturerService.findAllLecturer();

        if(response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-majors")
    public ResponseEntity<List<String>> getAllMajor() {
        List<String> specializes = specializeService.findAllSpecialize()
                                                    .stream()
                                                    .map(specialize -> specialize.getNameSpecialize())
                                                    .collect(Collectors.toList());
        if(specializes == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(specializes);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> actionWithSubject(@RequestParam(name = "subjectId") Long subjectId, 
                                    @RequestParam(name = "isDeleted") Boolean isDeleted) {

        if(subjectId == null || isDeleted == null) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            subjectService.softDelete(subjectId, isDeleted);
            return ResponseEntity.ok().build(); // Trả về 200 OK888888888888
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xóa không thành công: " + e.getMessage());
        }
    }

    @GetMapping("/get-interest-part-dtos/{courseId}")
    public ResponseEntity<List<InterestPartyDtoResponse>> getMethodName(@PathVariable Long courseId) {
        if(courseId == null) {
            throw new NullPointerException("CourseId is nulll");
        } else {
            List<InterestedParty> listInterestedParties = interestedPartyRepository.findByCourseId(courseId);
            List<InterestPartyDtoResponse> dtoList = listInterestedParties.stream()
                .map(interestedParty -> modelMapper.map(interestedParty, InterestPartyDtoResponse.class))
                .collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        }
    }

    @PostMapping("/tick-sale/{interestPartyId}")
    public ResponseEntity<?> tickSale(@PathVariable(name = "interestPartyId") Long interestPartyId) {
        if(interestPartyId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            try {
                InterestedParty interestedParty = interestedPartyRepository.findByInterestedPartyId(interestPartyId);

                interestedParty.setIsSaled(true);
                interestedPartyRepository.save(interestedParty);
                return ResponseEntity.ok().build();
            } catch(Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
    }
}
