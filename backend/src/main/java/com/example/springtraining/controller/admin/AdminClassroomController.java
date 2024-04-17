package com.example.springtraining.controller.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.dto.SelectCourseDto;
import com.example.springtraining.dto.request.AddClassroom;
import com.example.springtraining.dto.response.ClassroomResponse;
import com.example.springtraining.dto.response.StudentOfClassResponse;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Course;
import com.example.springtraining.mapper.EnrollmentMapper;
import com.example.springtraining.mapper.SelectCourseMapper;
import com.example.springtraining.service.ClassroomService;
import com.example.springtraining.service.CourseService;
import com.example.springtraining.service.EnrollmentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/course-management/admin/management-class")
@CrossOrigin(origins = "http://localhost:8081")
public class AdminClassroomController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/select-course")
    public ResponseEntity<List<SelectCourseDto>> getAllCourse() {
        List<Course> courses = courseService.findAllCourse();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        
        List<SelectCourseDto> courseDtos = courses.stream()
            .map(SelectCourseMapper::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(courseDtos);
    }

    @PostMapping("/add-classroom")
    public ResponseEntity<?> addClassroom(@RequestBody AddClassroom newClassroom) {
        if (newClassroom == null) {
            return ResponseEntity.badRequest().body("Dữ liệu lớp học trống");
        }

        Long courseId = newClassroom.getCourseId();
        if (courseId == null) {
            return ResponseEntity.badRequest().body("Bạn chưa chọn khoá học");
        }

        Course course = courseService.findByCourseId(courseId);
        if (course == null) {
            return ResponseEntity.badRequest().body("Khoá học không tồn tại");
        }

        String codeClassroom = newClassroom.getCodeClassroom();
        if (codeClassroom == null || codeClassroom.isEmpty()) {
            return ResponseEntity.badRequest().body("Mã lớp học không được để trống");
        }

        if (classroomService.findByCodeClassroom(codeClassroom) != null) {
            return ResponseEntity.badRequest().body("Mã lớp đã bị trùng");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime formattedBeginDate = LocalDateTime.parse(newClassroom.getBeginDate() + "T00:00:00", formatter);
            LocalDateTime formattedEndDate = LocalDateTime.parse(newClassroom.getEndDate() + "T00:00:00", formatter);

            Classroom classroom = new Classroom();
            classroom.setCodeClassroom(codeClassroom);
            classroom.setBeginDate(formattedBeginDate);
            classroom.setEndDate(formattedEndDate);
            classroom.setCourse(course); // Đặt khoá học cho lớp học

            // Cập nhật danh sách lớp học của khoá học
            course.addClassroom(classroom);

            classroomService.save(classroom);
            return ResponseEntity.ok().body("Thêm thành công");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Định dạng ngày không hợp lệ");
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<List<ClassroomResponse>> getAllClass(@PathVariable Long courseId) {
        List<ClassroomResponse> classroomResponses = courseService.findClassroomByCourseId(courseId);
        return ResponseEntity.ok(classroomResponses);
    }

    @GetMapping("/get-students/{classroomId}")
    public ResponseEntity<List<StudentOfClassResponse>> getStudentOfClass(@PathVariable Long classroomId) {
        if (classroomId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<StudentOfClassResponse> listStudentOfClassroom = enrollmentService.findEnrollmentByClassroomId(classroomId)
                .stream()
                .map(EnrollmentMapper::mapToDto)
                .collect(Collectors.toList());

        if (listStudentOfClassroom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listStudentOfClassroom);
    }
}
