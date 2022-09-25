package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.dto.ticket.TicketDto;
import com.github.youssefwadie.bugtracker.dto.ticket.TicketMapper;
import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import com.github.youssefwadie.bugtracker.security.UserContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tickets")
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
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDto newTicket) throws ConstraintsViolationException, URISyntaxException {
    	Ticket ticket = ticketMapper.dtoToModel(newTicket);
    	
    	return ResponseEntity.ok(null);
//        return ResponseEntity.created(resourceUri).build();
//        Ticket savedTicket = ticketService.save(ticketMapper.dtoToModel(newTicket));
//        URI resourceUri = new URI("http:localhost:8080/api/v1/tickets/" + savedTicket.getId());
//        return ResponseEntity.created(resourceUri).build();
    }
    
    
    @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> findTicketById(@PathVariable("id") Long id) {
    	Optional<Ticket> ticketOptional = ticketService.findById(id);
    	if(ticketOptional.isEmpty()) {
    		return ResponseEntity.notFound().build();
    	}
    	return ResponseEntity.ok(ticketOptional.get());
    }
    
    @PutMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateTicketById(@PathVariable("id") Long id, @RequestBody TicketDto ticketDto) {
    	return ResponseEntity.ok().build();
    }
    
}
