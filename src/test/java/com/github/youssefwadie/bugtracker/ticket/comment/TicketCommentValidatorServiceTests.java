package com.github.youssefwadie.bugtracker.ticket.comment;


import com.github.youssefwadie.bugtracker.model.TicketComment;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import com.github.youssefwadie.bugtracker.ticket.TicketRepository;
import com.github.youssefwadie.bugtracker.ticket.comment.dao.TicketCommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TicketCommentValidatorServiceTests {
    @MockBean
    TicketRepository ticketRepository;

    @MockBean
    TicketCommentRepository commentRepository;

    @Autowired
    TicketCommentValidatorService validatorService;


    @Test
    void validateUpdatingCommentWithDifferentCommenterId() {
        final long commenterId = 1L;
        final long commentId = 20L;
        final User commenter = new User(commenterId);
        final TicketComment comment = new TicketComment();
        comment.setId(commentId);
        comment.setCommenter(commenter);
        Mockito.when(commentRepository.createdByUser(commenter.getId(), commentId)).thenReturn(false);

        final ConstraintsViolationException ex = catchThrowableOfType(() -> {
            validatorService.validate(comment);
        }, ConstraintsViolationException.class);
        assertThat(ex).isNotNull();
        assertThat(ex.getErrors().containsKey("commenter.id")).isTrue();
        assertThat(ex.getErrors().get("commenter.id")).isEqualTo(TicketCommentValidatorService.INVALID_ACCESS_MSG);
    }

    @Test
    void validateNewValidComment() {
        final long commenterId = 1L;
        final long ticketId = 20L;
        final String content = "Hello, world";

        final User commenter = new User(commenterId);
        final TicketComment comment = new TicketComment();
        comment.setContent(content);
        comment.setCommenter(commenter);
        comment.setTicketId(ticketId);
        Mockito.when(ticketRepository.existsById(ticketId)).thenReturn(true);

        final ConstraintsViolationException ex = catchThrowableOfType(() -> {
            validatorService.validate(comment);
        }, ConstraintsViolationException.class);
        assertThat(ex).isNull();
    }

    @Test
    void validateUpdatingValidComment() {
        final long commentId = 1L;
        final long commenterId = 1L;
        final long ticketId = 20L;
        final String content = "Hello, world";

        final User commenter = new User(commenterId);
        final TicketComment comment = new TicketComment();
        comment.setId(commentId);
        comment.setContent(content);
        comment.setCommenter(commenter);
        comment.setTicketId(ticketId);
        Mockito.when(ticketRepository.existsById(ticketId)).thenReturn(true);
        Mockito.when(commentRepository.createdByUser(commentId, commenterId)).thenReturn(true);
        final ConstraintsViolationException ex = catchThrowableOfType(() -> {
            validatorService.validate(comment);
        }, ConstraintsViolationException.class);
        assertThat(ex).isNull();
    }


    @Test
    void validateNewCommentWithInvalidTicketId() {
        final long commenterId = 1L;
        final long ticketId = 5000L;

        final User commenter = new User(commenterId);
        final TicketComment comment = new TicketComment();
        comment.setCommenter(commenter);
        comment.setTicketId(ticketId);
        Mockito.when(ticketRepository.existsById(ticketId)).thenReturn(false);

        final ConstraintsViolationException ex = catchThrowableOfType(() -> {
            validatorService.validate(comment);
        }, ConstraintsViolationException.class);
        assertThat(ex).isNotNull();
        assertThat(ex.getErrors().containsKey("ticketId")).isTrue();
        assertThat(ex.getErrors().get("ticketId")).isEqualTo(String.format(TicketCommentValidatorService.INVALID_TICKET_ID, ticketId));
    }

}
