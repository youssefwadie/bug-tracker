package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.model.TicketPriority;
import com.github.youssefwadie.bugtracker.model.TicketStatus;
import com.github.youssefwadie.bugtracker.model.TicketType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.submitterId = ?1")
    List<Ticket> findAllByUserId(Long userId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Ticket t WHERE t.id = ?1 AND t.submitterId = ?2")
    boolean isCreatedByUser(Long ticketId, Long userId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.submitterId = ?1")
    Long countBySubmitterId(Long submitterId);

    @Query("SELECT t FROM Ticket t WHERE t.submitterId = ?1")
    Page<Ticket> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Ticket t WHERE t.id = ?1 AND t.project.id = ?2")
    boolean existsByIdAndProjectId(Long id, Long projectId);

    @Modifying
    @Query("UPDATE Ticket SET createdAt = ?2 WHERE id = ?1")
    void setCreatedAt(Long id, LocalDateTime createdAt);

    @Query("SELECT COUNT(t) FROM Ticket t")
    long count();

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.submitterId = ?1 AND t.status = ?2")
    long countBySubmitterIdAndStatus(Long submitterId, TicketStatus status);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.submitterId = ?1 AND t.type = ?2")
    long countBySubmitterIdAndType(Long submitterId, TicketType type);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.submitterId = ?1 AND t.priority = ?2")
    long countBySubmitterIdAndPriority(Long submitterId, TicketPriority priority);

}
