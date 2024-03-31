package com.example.springtraining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springtraining.entity.Specialize;

public interface SpecializeRepository extends JpaRepository<Specialize, Long> {
    List<Specialize> findAll();
}
