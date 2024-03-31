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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Specialize")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Specialize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specializeId; 

    private String nameSpecialize;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "lecturerId")
    private Lecturer lecturer;
}
