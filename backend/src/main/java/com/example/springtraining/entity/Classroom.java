package com.example.springtraining.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Classroom")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroomId;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.EAGER)
    private List<Enrollment> enrollments;

    private LocalDateTime beginDate;

    private String codeClassroom;

    private LocalDateTime endDate;

    private LocalDateTime examDate;
    // private LocalDateTime studyTime;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private Set<Assignment> assignments;

     // add enrollment
     public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        enrollment.setClassroom(this);
    }
}
