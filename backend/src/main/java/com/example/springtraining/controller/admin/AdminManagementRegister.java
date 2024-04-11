package com.example.springtraining.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.config.service.MailSenderService;
import com.example.springtraining.dto.CreateAccountDto;
import com.example.springtraining.dto.RegisterQueueResponse;
import com.example.springtraining.entity.Classroom;
import com.example.springtraining.entity.Enrollment;
import com.example.springtraining.entity.Register;
import com.example.springtraining.entity.User;
import com.example.springtraining.repository.ClassroomRepository;
import com.example.springtraining.repository.EnrollmentRepository;
import com.example.springtraining.repository.RegisterRepository;
import com.example.springtraining.repository.UserRepository;

import jakarta.mail.MessagingException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/course-management/admin")
public class AdminManagementRegister {
    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @GetMapping("/get-register-in-class")
    public ResponseEntity<List<RegisterQueueResponse>> getRegisterInClass(@RequestParam(name = "classroomId") Long classroomId, @RequestParam(name = "courseId") Long courseId) {
        if(classroomId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            try {
                List<Register> registers = registerRepository.findByClassroomId(classroomId, courseId);

                List<RegisterQueueResponse> registerQueueResponses = registers.stream()
                                                                         .map(register -> modelMapper.map(register, RegisterQueueResponse.class))
                                                                         .collect(Collectors.toList());
            
                return ResponseEntity.ok(registerQueueResponses);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @PreAuthorize("permitAll")
    @PostMapping("/add-register/{registerId}")
    public ResponseEntity<?> addRegister(@PathVariable(name = "registerId") Long registerId, @RequestBody String checkboxAdd) {
        if(registerId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            try {
                Boolean checkboxAddFormatted = Boolean.parseBoolean(checkboxAdd);
                Register register = registerRepository.findByRegisterId(registerId);
                register.setAddedClassroom(checkboxAddFormatted);
                registerRepository.save(register);
                return ResponseEntity.ok(HttpStatus.OK);
            } catch(Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
    } 

    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountDto createAccountDto) throws MessagingException {
        String email = createAccountDto.getEmail();
        String phone = createAccountDto.getPhone();

        Long registerId = createAccountDto.getRegisterId();
        Register register = registerRepository.findByRegisterId(registerId);
        register.setCreatedAccount(true);
        registerRepository.save(register);

        Long classroomId = createAccountDto.getClassroomId();
        Classroom classroom = classroomRepository.findByClassroomId(classroomId);

        // create account 
        User existedUser = userRepository.findByEmailUser(email);
        String randomString = UUID.randomUUID().toString();
        String password = "Cls*" + randomString;
        System.out.println("Password: " + password);

        if(existedUser == null) {
            // save user
            User user = new User();
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(register.getAddress());
            user.setRole("USER");

            // save user
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);

            // save classroom
            classroomRepository.save(classroom);
            
            // save enrollment
            Enrollment enrollment = new Enrollment();
            enrollment.setClassroom(classroom);
            enrollment.setUser(user);
            enrollmentRepository.save(enrollment);
        } else {
            existedUser.setPassword(passwordEncoder.encode(password));
            userRepository.save(existedUser);

            // save classroom
            classroomRepository.save(classroom);
            
            // save enrollment
            Enrollment enrollment = new Enrollment();
            enrollment.setClassroom(classroom);
            enrollment.setUser(existedUser);
            enrollmentRepository.save(enrollment);
        }

        // send email
        String subject = "Tài khoản đăng nhập khoá học của bạn ";
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
                + "<h1>Tài khoản đăng nhập hệ thống Khoá học 247 </h1>"
                + "<p>Cảm ơn bạn đã quan tâm tới khoá học của chúng tôi. Đây là mật khẩu đăng nhập khoá học của bạn.</p>"
                + "<p>Thông tin đăng nhập:</p>"
                + "<ul>"
                + "<li><strong>Email:</strong> " + createAccountDto.getEmail() + "</li>"
                + "<li><strong>Mật khẩu:</strong> " + password + "</li>"
                + "</ul>"
                + "<p>Vui lòng đổi mật khẩu ngay khi nhận được tài khoản. Một lần nữa, cảm ơn bạn đã đăng ký!</p>"
                + "</div>"
                + "</body>"
                + "</html>";

        mailSenderService.sendNewMail(email, subject, body);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
}
