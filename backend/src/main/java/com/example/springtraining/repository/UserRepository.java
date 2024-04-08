package com.example.springtraining.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springtraining.entity.User;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    User findByUserId(Long userId);

    @Transactional
    User save(User user);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    @Transactional
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmailUser(String email);


}
