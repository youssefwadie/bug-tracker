package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketValidatorService validatorService;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket save(Ticket ticket) throws ConstraintViolationException {
        validatorService.validateTicket(ticket);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> findAllBySubmittedUserId(Long userId) {
        return ticketRepository.findAllByUserId(userId);
    }
}
