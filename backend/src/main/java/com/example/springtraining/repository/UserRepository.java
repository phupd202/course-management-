package com.example.springtraining.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springtraining.entity.User;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);

    User save(User user);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);
}
