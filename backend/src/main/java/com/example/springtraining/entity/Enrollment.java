package com.example.springtraining.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Enrollment")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "classroomId")
    private Classroom classroom;

    private LocalDateTime enrollmentDay;

    private Integer score;
     
    private String status;

    @OneToOne(mappedBy = "enrollment", fetch = FetchType.EAGER)
    private Subject subject;
    
    public void addSubject(Subject subject) {
        this.subject = subject;
        subject.setEnrollment(this);
    }
}
