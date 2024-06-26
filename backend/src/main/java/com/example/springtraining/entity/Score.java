package com.example.springtraining.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Score")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "subjectId")
    private Subject subject;

    private String status; // khá, giỏi , xuất sắc

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "classroomId")
    private Classroom classroom;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId")
    private User user;

    private Integer score;
}
