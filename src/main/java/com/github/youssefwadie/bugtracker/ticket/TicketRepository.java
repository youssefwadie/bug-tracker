package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
