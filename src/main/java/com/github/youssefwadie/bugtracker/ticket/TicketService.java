package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TicketService {
    private static final int TICKETS_PER_PAGE = 10;

    public static final String TICKET_NOT_FOUND_MSG = "no ticket with id %d is found";

    private final TicketRepository ticketRepository;
    private final TicketValidatorService validatorService;

    public Ticket save(Ticket ticket) throws ConstraintsViolationException {
        validatorService.validateTicket(ticket);
        ticket.setCreatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    public List<Ticket> findAllBySubmitterId(Long userId) {
        return ticketRepository.findAllByUserId(userId);
    }

    public Long countBySubmitterId(Long userId) {
        return ticketRepository.countBySubmitterId(userId);
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
        validatorService.validateTicketBeforeUpdate(ticket);

        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> listByPage(long userId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, TICKETS_PER_PAGE);
        Page<Ticket> ticketsPage = ticketRepository.findAllByUserId(userId, pageable);
        return ticketsPage.getContent();
    }
}
