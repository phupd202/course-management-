package com.example.springtraining.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springtraining.config.service.MailSenderService;
import com.example.springtraining.dto.email.EmailRequestDto;
import com.example.springtraining.dto.email.EmailUpdateDto;
import com.example.springtraining.entity.MailTemplate;
import com.example.springtraining.repository.MailTemplateRepository;

import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/course-management/admin")
@CrossOrigin(origins = "http://localhost:8081")
public class AdminMailTemplateController {
    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private MailTemplateRepository mailTemplateRepository;

    @PostMapping("/add-template-mail")
    public ResponseEntity<?> addTemplateMail(@RequestBody EmailRequestDto emailRequestDto) {
        if (emailRequestDto == null) {
            return ResponseEntity.badRequest().build();
        }

        MailTemplate emailTemplate = new MailTemplate();
        try {
            emailTemplate.setContent(emailRequestDto.getContent());
            emailTemplate.setNameTemplate(emailRequestDto.getNameTemplate());
            emailTemplate.setSubject(emailRequestDto.getSubject());

            mailTemplateRepository.save(emailTemplate);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get-all-template")
    public ResponseEntity<List<MailTemplate>> getAllTemplate() {
        try {
            List<MailTemplate> mailTemplates = mailTemplateRepository.findAll();
            return ResponseEntity.ok(mailTemplates);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/save-change-template")
    public ResponseEntity<?> saveChangeTemplate(@RequestBody EmailUpdateDto emailUpdateDto) {
        try {
            Long mailTemplateId = emailUpdateDto.getMailTemplateId();
            MailTemplate mailTemplate = mailTemplateRepository.findByMailTemplateId(mailTemplateId);

            mailTemplate.setContent(emailUpdateDto.getContent());
            mailTemplate.setSubject(emailUpdateDto.getSubject());
            mailTemplate.setNameTemplate(emailUpdateDto.getNameTemplate());
            mailTemplateRepository.save(mailTemplate);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get-email-with-id/{templateMailId}")
    public ResponseEntity<MailTemplate> getEmailWithId(@PathVariable(name = "templateMailId") Long templateMailId) {
        try {
            MailTemplate mailTemplate = mailTemplateRepository.findByMailTemplateId(templateMailId);
            return ResponseEntity.ok(mailTemplate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/send-mail/{email}")
    public ResponseEntity<?> sendMail(@PathVariable(name = "email") String email,
            @RequestBody MailTemplate mailTemplate) {
        try {
            String body = mailTemplate.getContent();
            String subject = mailTemplate.getSubject();
            mailSenderService.sendNewMail(email, subject, body);
            return ResponseEntity.ok(mailTemplate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete-mail/{mailTemplateId}")
    public ResponseEntity<?> deleteMail(@PathVariable(name = "mailTemplateId") Long mailTemplateId) {
        try {
            MailTemplate mailTemplate = mailTemplateRepository.findByMailTemplateId(mailTemplateId);

            if(mailTemplate == null ) {
                return ResponseEntity.notFound().build();
            } else {
                mailTemplateRepository.delete(mailTemplate);
                return ResponseEntity.ok("Delete success");
            }
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
}
