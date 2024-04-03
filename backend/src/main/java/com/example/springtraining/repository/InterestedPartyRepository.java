package com.example.springtraining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.InterestedParty;

@Repository
public interface InterestedPartyRepository extends JpaRepository<InterestedParty, Long>{
    @Transactional
    InterestedParty save(InterestedParty interestedParty);

    @Transactional
    @Query("SELECT ip FROM InterestedParty ip JOIN ip.course c WHERE c.courseId = :courseId")
    List<InterestedParty> findByCourseId(Long courseId);

    @Transactional
    InterestedParty findByInterestedPartyId(Long interestedPartyId);
    
}
