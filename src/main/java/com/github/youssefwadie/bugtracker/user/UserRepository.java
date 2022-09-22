package com.github.youssefwadie.bugtracker.user;

import com.github.youssefwadie.bugtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = ?1")
    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.enabled = true")
    void enableUserById(Long userId);
}
