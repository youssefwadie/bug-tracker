package com.github.youssefwadie.bugtracker.ticket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.youssefwadie.bugtracker.dto.ticket.TicketDto;
import com.github.youssefwadie.bugtracker.dto.ticket.TicketMapper;
import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.UserContextHolder;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    private final TicketMapper ticketMapper;

    @GetMapping("")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        User loggedInUser = UserContextHolder.get();
        List<Ticket> usersTickets = ticketService.findAllBySubmittedUserId(loggedInUser.getId());
        return ResponseEntity.ok(ticketMapper.modelsToDtos(usersTickets));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDto newTicket) throws ConstraintsViolationException, URISyntaxException {
        newTicket.setSubmitter(UserContextHolder.get().getId());
        Ticket savedTicket = ticketService.save(ticketMapper.dtoToModel(newTicket));
        URI resourceUri = new URI("http://localhost:8080/api/v1/tickets/" + savedTicket.getId());
        return ResponseEntity.created(resourceUri).build();
    }


    @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketDto> findTicketById(@PathVariable("id") Long id) {
        Optional<Ticket> ticketOptional = ticketService.findById(id);
        if (ticketOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ticketMapper.modelToDto(ticketOptional.get()));
    }

    @PutMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketDto> updateTicketById(@PathVariable("id") Long id, @RequestBody TicketDto ticketDto) {
        Ticket ticket = ticketMapper.dtoToModel(ticketDto);
        User loggedInUser = UserContextHolder.get();
        ticket.setId(id);
        ticket.setSubmitterId(loggedInUser.getId());
        Ticket savedTicket = ticketService.update(ticket);
        return ResponseEntity.ok(ticketMapper.modelToDto(savedTicket));
    }

}
