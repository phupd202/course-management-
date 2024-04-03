package com.example.springtraining.entity;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "Subject")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "courseId")
    private Course course;

    private String nameSubject;

    private String content;

    private Integer estimate;

    // lưu trữ các nhãn dành riêng cho môn học
    private String label;

    private Boolean isDeleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    @Fetch(FetchMode.JOIN)
    Set<Assignment> assignments;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "enrollmentId")
    private Enrollment enrollment;

}


