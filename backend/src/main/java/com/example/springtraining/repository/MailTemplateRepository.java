package com.example.springtraining.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.MailTemplate;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {
    @Transactional
    MailTemplate save(MailTemplate mailTemplate);

    @Transactional
    List<MailTemplate> findAll();

    @Transactional
    MailTemplate findByMailTemplateId(Long mailTemplateId);
}
