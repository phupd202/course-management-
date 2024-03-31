package com.example.springtraining.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Lecturer")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lecturerId;

    private String nameLecture;

    private String email;

    private String phone;

    private Integer limitHours;

    @OneToMany(mappedBy = "lecturer", fetch = FetchType.LAZY)
    Set<Assignment> assignments;

    @OneToMany(mappedBy = "lecturer", fetch = FetchType.LAZY)
    Set<Specialize> specializes;
}
