package com.github.youssefwadie.bugtracker.ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TicketService {
    public static final String TICKET_NOT_FOUND_MSG = "no ticket with id %d is found";

    private final TicketRepository ticketRepository;
    private final TicketValidatorService validatorService;

    public Ticket save(Ticket ticket) throws ConstraintsViolationException {
        validatorService.validateTicket(ticket);
        ticket.setCreatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    public List<Ticket> findAllBySubmittedUserId(Long userId) {
        return ticketRepository.findAllByUserId(userId);
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    public boolean isCreatedByUser(Long ticketId, Long userId) {
        if (ticketId == null || userId == null) return false;
        return ticketRepository.isCreatedByUser(ticketId, userId);
    }

    public Ticket update(Ticket ticket) {
        if (!isCreatedByUser(ticket.getId(), ticket.getSubmitterId())) {
            throw new TicketNotFoundException(String.format(TICKET_NOT_FOUND_MSG, ticket.getId()));
        }

        validatorService.validateTicket(ticket);

        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }
}
