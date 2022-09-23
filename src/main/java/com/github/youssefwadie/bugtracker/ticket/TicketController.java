package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.dto.ticket.TicketDto;
import com.github.youssefwadie.bugtracker.dto.ticket.TicketMapper;
import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintViolationException;
import com.github.youssefwadie.bugtracker.security.UserContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {
    private final TicketService ticketService;

    private final TicketMapper ticketMapper;

    @GetMapping("")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        User loggedInUser = UserContextHolder.get();
        if (loggedInUser != null) {
            return ResponseEntity.ok(ticketService.findAllBySubmittedUserId(loggedInUser.getId()));
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDto newTicket) throws ConstraintViolationException {
        Ticket savedTicket = ticketService.save(ticketMapper.dtoToModel(newTicket));
        return ResponseEntity.ok(savedTicket);
    }
}
