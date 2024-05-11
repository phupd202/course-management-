package com.example.springtraining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.PersonalEvent;

@Repository
public interface PersonalEventRepository extends JpaRepository<PersonalEvent, Long>{
    @Transactional
    PersonalEvent save(PersonalEvent personalEvent);

    @Transactional
    @Query("SELECT pe FROM PersonalEvent pe JOIN pe.lecturer lec WHERE lec.email = :email")
    List<PersonalEvent> findPersonalEventOfLecturer(String email);
}
