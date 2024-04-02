package com.example.springtraining.controller.user;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.config.service.MailSenderService;
import com.example.springtraining.dto.guest.InterestedPartyDto;
import com.example.springtraining.dto.response.CourseResponse;
import com.example.springtraining.entity.Course;
import com.example.springtraining.entity.InterestedParty;
import com.example.springtraining.mapper.CourseResponseMapper;
import com.example.springtraining.repository.CourseRepository;
import com.example.springtraining.repository.InterestedPartyRepository;

import jakarta.mail.MessagingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/course-management")

public class AllCourseController {
    private final CourseRepository courseRepository;

    private final MailSenderService mailSenderService;

    private final InterestedPartyRepository interestedPartyRepository;

    private static final Logger logger = LoggerFactory.getLogger(AllCourseController.class);

    public AllCourseController(CourseRepository courseRepository, MailSenderService mailSenderService,
            InterestedPartyRepository interestedPartyRepository) {
        this.courseRepository = courseRepository;
        this.mailSenderService = mailSenderService;
        this.interestedPartyRepository = interestedPartyRepository;
    }

    @GetMapping("/get-all-courses")
    public ResponseEntity<List<CourseResponse>> getAllCourse() {
        List<Course> courses = courseRepository.findAllCourse();
        if (courses == null || courses.isEmpty()) {
            logger.debug("Course is null or empty");
            return ResponseEntity.noContent().build();
        } else {
            List<CourseResponse> courseResponses = courses.stream()
                    .map(CourseResponseMapper::mapToDto)
                    .collect(Collectors.toList());
            logger.info("Get course successfully!");
            return ResponseEntity.ok(courseResponses);
        }
    }

    @PostMapping("/interest-course")
    public ResponseEntity<?> register(@RequestBody InterestedPartyDto interestedPartyDto) throws MessagingException {
        logger.debug(interestedPartyDto.toString());
        
        if (interestedPartyDto == null) {
            return ResponseEntity.noContent().build();
        }

        String email = interestedPartyDto.getEmail();

        if (email != null) {
            // Tiêu đề của email
            String subject = "Xác nhận đăng ký khoá học";
            logger.debug(interestedPartyDto.toString());

            Course course = courseRepository.findByCourseId(interestedPartyDto.getCourseId());
            
            // Nội dung của email, sử dụng HTML và CSS để trình bày
            String body = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; background-color: #f9f9f9; }"
                    + ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 10px; box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1); }"
                    + "h1 { color: #333; }"
                    + "p { color: #666; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='container'>"
                    + "<h1>Thư xác nhận đăng ký khoá học</h1>"
                    + "<p>Cảm ơn bạn đã quan tâm tới khoá học của chúng tôi. Chúng tôi gửi thư này để xác nhận thông tin đăng ký của bạn.</p>"
                    + "<p>Thông tin đăng ký:</p>"
                    + "<ul>"
                    + "<li><strong>Tên khoá học:</strong> " + course.getNameCourse() + "</li>"
                    + "<li><strong>Họ và tên:</strong> " + interestedPartyDto.getName() + "</li>"
                    + "<li><strong>Email:</strong> " + interestedPartyDto.getEmail() + "</li>"
                    + "<li><strong>Điện thoại:</strong> " + interestedPartyDto.getPhone() + "</li>"
                    + "<li><strong>Địa chỉ:</strong> " + interestedPartyDto.getAddress() + "</li>"
                    + "</ul>"
                    + "<p>Chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất có thể. Cảm ơn bạn đã đăng ký!</p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
                    
            mailSenderService.sendNewMail(email, subject, body);

            InterestedParty interestedParty = new InterestedParty();
            interestedParty.setName(interestedPartyDto.getName());
            interestedParty.setPhone(interestedPartyDto.getPhone());
            interestedParty.setEmail(interestedPartyDto.getEmail());
            interestedParty.setAddress(interestedPartyDto.getAddress());
            interestedParty.setCourse(course);
            courseRepository.save(course);
            interestedPartyRepository.save(interestedParty);

            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

}
