package com.example.springtraining.entity;

import jakarta.persistence.Entity;
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
@Table(name = "InterestedParty")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class InterestedParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestedPartyId;

    private String name; 

    private String phone; 

    private String address;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
}
