package com.example.springtraining.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "PersonalEvent")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonalEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalEventId;

    private String title;

    private LocalDate date;

    private LocalTime beginTime;

    private LocalTime endTime;

    private String takenote;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lecturerId")
    private Lecturer lecturer;
}
