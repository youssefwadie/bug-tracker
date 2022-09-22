package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.dto.TicketDto;
import com.github.youssefwadie.bugtracker.dto.TicketMapper;
import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
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
    private final TicketValidatorService validatorService;

    private final TicketMapper ticketMapper;

    @GetMapping("")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDto newTicket) throws ConstraintsViolationException {
        Ticket savedTicket = ticketService.save(ticketMapper.dtoToModel(newTicket));
        return ResponseEntity.ok(savedTicket);
    }
}
