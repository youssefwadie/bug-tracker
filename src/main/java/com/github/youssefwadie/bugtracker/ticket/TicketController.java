package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.dto.mappers.TicketMapper;
import com.github.youssefwadie.bugtracker.dto.model.TicketDto;
import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.UserContextHolder;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static com.github.youssefwadie.bugtracker.constants.ResponseConstants.TOTAL_COUNT_HEADER_NAME;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    private final TicketMapper ticketMapper;

    // TODO: delete -- just for testing
    @GetMapping("")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        User loggedInUser = UserContextHolder.get();
        List<Ticket> usersTickets = ticketService.findAllBySubmitterId(loggedInUser.getId());
        return ResponseEntity.ok(ticketMapper.ticketsToTicketsDto(usersTickets));
    }

    @GetMapping("/page/{pageNumber:\\d+}")
    public ResponseEntity<List<TicketDto>> listByPage(@PathVariable("pageNumber") Integer pageNumber) {
        User loggedInUser = UserContextHolder.get();
        Page<Ticket> ticketsPage = ticketService.getPage(loggedInUser.getId(), pageNumber);
        return ResponseEntity.ok()
                .header(TOTAL_COUNT_HEADER_NAME, Long.toString(ticketsPage.getTotalElements()))
                .body(ticketMapper.ticketsToTicketsDto(ticketsPage.getContent()));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDto newTicket) throws ConstraintsViolationException, URISyntaxException {
        newTicket.setSubmitter(UserContextHolder.get().getId());
        Ticket savedTicket = ticketService.save(ticketMapper.ticketDtoToTicket(newTicket));
        URI resourceUri = new URI("http://localhost:8080/api/v1/tickets/" + savedTicket.getId());
        return ResponseEntity.created(resourceUri).build();
    }


    @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketDto> findTicketById(@PathVariable("id") Long id) {
        Optional<Ticket> ticketOptional = ticketService.findById(id);
        if (ticketOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ticketMapper.ticketToTicketDto(ticketOptional.get()));
    }

    @PutMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketDto> updateTicketById(@PathVariable("id") Long id, @RequestBody TicketDto ticketDto) {
        Ticket ticket = ticketMapper.ticketDtoToTicket(ticketDto);
        User loggedInUser = UserContextHolder.get();
        ticket.setId(id);
        ticket.setSubmitterId(loggedInUser.getId());
        Ticket savedTicket = ticketService.update(ticket);
        return ResponseEntity.ok(ticketMapper.ticketToTicketDto(savedTicket));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTicketsCount() {
        User loggedInUser = UserContextHolder.get();
        return ResponseEntity.ok(ticketService.countBySubmitterId(loggedInUser.getId()));
    }


}
