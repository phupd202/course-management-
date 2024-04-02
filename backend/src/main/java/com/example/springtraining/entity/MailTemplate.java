package com.example.springtraining.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MailTemplate")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mailTemplateId; 

    private String nameTemplate;

    private String subject;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
