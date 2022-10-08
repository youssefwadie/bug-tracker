package com.github.youssefwadie.bugtracker.ticket.comment;

import com.github.youssefwadie.bugtracker.model.TicketComment;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import com.github.youssefwadie.bugtracker.ticket.TicketRepository;
import com.github.youssefwadie.bugtracker.ticket.comment.dao.TicketCommentRepository;
import com.github.youssefwadie.bugtracker.util.BeanValidationUtils;
import com.github.youssefwadie.bugtracker.util.PropertyValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TicketCommentValidatorService {
    private static final int MAX_COMMENT_LENGTH = 255;
    private static final String INVALID_CONTENT_MSG = "Cannot be empty, max size is " + MAX_COMMENT_LENGTH;
    public static final String INVALID_ACCESS_MSG = "the comment is not created by the user";
    public static final String INVALID_TICKET_ID = "no ticket with id = %d was found";
    private static final String MUST_BE_SET_MSG = "must be set";
    private final TicketCommentRepository commentRepository;
    private final TicketRepository ticketRepository;

    public void validate(TicketComment comment) {
        final List<PropertyValidationResult> validationResults = new ArrayList<>();
        final boolean updating = comment.getId() != null;

        validationResults.add(checkContent(comment.getContent()));
        validationResults.add(checkCommenter(comment.getCommenter(), comment.getId(), updating));
        if (!updating) {
            validationResults.add(checkTicketId(comment.getTicketId()));
        }

        Map<String, String> errors = BeanValidationUtils.aggregateValidationResults(validationResults);
        if (!errors.isEmpty()) {
            throw new ConstraintsViolationException(errors);
        }
    }


    private PropertyValidationResult checkTicketId(Long ticketId) {
        if (ticketId == null) {
            return new PropertyValidationResult("ticketId", MUST_BE_SET_MSG);
        }

        if (!ticketRepository.existsById(ticketId)) {
            return new PropertyValidationResult("ticketId", String.format(INVALID_TICKET_ID, ticketId));
        }

        return PropertyValidationResult.VALID_VALIDATION_RESULT;
    }

    private PropertyValidationResult checkCommenter(User commenter, Long commentId, boolean updating) {
        if (commenter == null) {
            return new PropertyValidationResult("commenter", MUST_BE_SET_MSG);
        }
        if (commenter.getId() == null) {
            return new PropertyValidationResult("commenter.id", MUST_BE_SET_MSG);
        }
        if (updating && (commentId != null && !commentRepository.createdByUser(commentId, commenter.getId()))) {
            return new PropertyValidationResult("commenter.id", INVALID_ACCESS_MSG);
        }

        return PropertyValidationResult.VALID_VALIDATION_RESULT;
    }

    private PropertyValidationResult checkContent(String content) {
        return (content == null || content.isBlank() || content.length() > MAX_COMMENT_LENGTH)
                ? new PropertyValidationResult("content", INVALID_CONTENT_MSG)
                : PropertyValidationResult.VALID_VALIDATION_RESULT;
    }
}
