package com.github.youssefwadie.bugtracker.user.confirmationtoken;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.youssefwadie.bugtracker.model.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Query("SELECT cf FROM ConfirmationToken cf WHERE cf.token = ?1")
    Optional<ConfirmationToken> findByToken(String token);

    @Modifying
    @Query("DELETE FROM ConfirmationToken cf WHERE cf.userId = ?1")
    void deleteAllByUserId(Long userId);

    @Modifying
    @Query("UPDATE ConfirmationToken cf SET cf.confirmed = true WHERE cf.id = ?1")
    void setEnabledById(Long id);

}
