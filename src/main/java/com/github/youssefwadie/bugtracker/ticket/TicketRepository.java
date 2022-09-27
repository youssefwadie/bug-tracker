package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.submitterId = ?1")
    List<Ticket> findAllByUserId(Long userId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Ticket t WHERE t.id = ?1 AND t.submitterId = ?2")
    boolean isCreatedByUser(Long ticketId, Long userId);
}
