package com.example.springtraining.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.InterestedParty;

@Repository
public interface InterestedPartyRepository extends JpaRepository<InterestedParty, Long>{
    @Transactional
    InterestedParty save(InterestedParty interestedParty);
    
}
