package com.github.youssefwadie.bugtracker.ticket;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.youssefwadie.bugtracker.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.submitterId = ?1")
    List<Ticket> findAllByUserId(Long userId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Ticket t WHERE t.id = ?1 AND t.submitterId = ?2")
    boolean isCreatedByUser(Long ticketId, Long userId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.submitterId = ?1")
    Long countBySumbitterId(Long sumbitterId);
    
    @Query("SELECT t FROM Ticket t WHERE t.submitterId = ?1")
    Page<Ticket> findAllByUserId(Long userId, Pageable pageable);
}
