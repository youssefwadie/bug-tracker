package com.github.youssefwadie.bugtracker.ticket.comment;

import com.github.youssefwadie.bugtracker.model.TicketComment;
import com.github.youssefwadie.bugtracker.ticket.comment.dao.TicketCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TicketCommentService {
    public static final int COMMENTS_PER_PAGE = 5;

    private final TicketCommentRepository repository;
    private final TicketCommentValidatorService validatorService;


    TicketComment save(TicketComment comment) {
        validatorService.validate(comment);
        return repository.save(comment);
    }

    Page<TicketComment> findAllByTicket(Long ticketId, Integer pageNumber) {
        final Pageable pageable = PageRequest.of(pageNumber, COMMENTS_PER_PAGE);
        return repository.findAllByTicket(ticketId, pageable);
    }


    void deleteById(Long commentId) {
        repository.deleteById(commentId);
    }

}
