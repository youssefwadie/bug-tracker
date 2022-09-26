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
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = ?1")
    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.enabled = true")
    void enableUserById(Long userId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.id = ?1")
    boolean existsById(Long id);
    
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 'true' ELSE 'false' END FROM works_on WHERE user_id = ?1 AND project_id = ?2" , nativeQuery = true)
	boolean doesUserWorkOnProject(Long userId, Long projectId);
    
    @Query(value = "INSERT INTO works_on (`project_id`,`user_id`) VALUES (?2, ?1)", nativeQuery = true)
	void addUserToProjectTeamMembers(Long userId, Long projectId);
}
