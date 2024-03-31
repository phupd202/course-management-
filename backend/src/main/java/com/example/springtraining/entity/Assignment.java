package com.example.springtraining.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Assignment")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "classroomId")
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Lecturer lecturer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Subject subject;

    private LocalTime beginTime;

    private LocalTime endTime;

    private Integer dayOfWeek;

    private Boolean isClosed;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;
}
