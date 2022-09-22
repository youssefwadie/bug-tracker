package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Service
public class TicketValidatorService {
    private final static int MIN_TITLE_LENGTH = 10;
    private final static int MAX_TITLE_LENGTH = 255;
    private final static int MIN_DESCRIPTION_LENGTH = 10;

    public static final String INVALID_TITLE_MSG = String.format("title must be at least %d characters and at most %d", MIN_TITLE_LENGTH, MAX_TITLE_LENGTH);
    public static final String INVALID_DESCRIPTION_MSG = String.format("description must be at least %d characters", MIN_DESCRIPTION_LENGTH);

    public void validateTicket(Ticket ticket) throws ConstraintsViolationException {
        Assert.notNull(ticket, "ticket cannot be null");
        final Map<String, String> errors = new HashMap<>();
        if (!isValidTitle(ticket.getTitle())) {
            errors.put("title", INVALID_TITLE_MSG);
        }
        if (!isValidDescription(ticket.getDescription())) {
            errors.put("description", INVALID_DESCRIPTION_MSG);
        }
        if (ticket.getSubmitter() == null) {
            errors.put("submitter", "cannot be empty");
        }

        if (!errors.isEmpty()) {
            throw new ConstraintsViolationException(errors);
        }
    }


    private boolean isValidTitle(String title) {
        if (title == null) return false;
        int titleLength = title.length();
        return titleLength >= MIN_TITLE_LENGTH && titleLength <= MAX_TITLE_LENGTH;
    }

    private boolean isValidDescription(String description) {
        return description != null && description.length() >= MIN_DESCRIPTION_LENGTH;
    }

}
