package com.github.youssefwadie.bugtracker.ticket;

import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.project.dao.ProjectRepository;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TicketValidatorService {
    private final static int MIN_TITLE_LENGTH = 10;
    private final static int MAX_TITLE_LENGTH = 255;
    private final static int MIN_DESCRIPTION_LENGTH = 10;

    private static final String INVALID_TITLE_MSG = String.format("title must be at least %d characters and at most %d", MIN_TITLE_LENGTH, MAX_TITLE_LENGTH);
    private static final String INVALID_DESCRIPTION_MSG = String.format("description must be at least %d characters", MIN_DESCRIPTION_LENGTH);

    //    private static final String INVALID_TYPE_MSG = String.format("unknown type. must be one of: %s", Arrays.toString(TicketType.values()));
//    private static final String INVALID_PRIORITY_MSG = String.format("unknown priority. must be one of: %s", Arrays.toString(TicketPriority.values()));
//    private static final String INVALID_STATUS_MSG = String.format("unknown status. must be one of: %s", Arrays.toString(TicketStatus.values()));
    private static final String INVALID_PROJECT_MSG = "Invalid project number";
    private static final String MISSING_PROPERTY_MSG = "cannot be empty";
    private static final String INVALID_PROJECT_ID_MSG = "invalid id";

    private final ProjectRepository projectRepository;
    private final TicketRepository ticketRepository;

    /**
     * validates a new ticket before it's saved to the database.
     *
     * @param ticket the ticket to be validated
     * @throws ConstraintsViolationException if any constraint is violated
     */
    public void validateTicket(Ticket ticket) throws ConstraintsViolationException {
        Assert.notNull(ticket, "ticket cannot be null");
        final Map<String, String> errors = new HashMap<>();
        if (!isValidTitle(ticket.getTitle())) {
            errors.put("title", INVALID_TITLE_MSG);
        }
        if (!isValidDescription(ticket.getDescription())) {
            errors.put("description", INVALID_DESCRIPTION_MSG);
        }
        if (ticket.getSubmitterId() == null) {
            errors.put("submitter", MISSING_PROPERTY_MSG);
        }
        if (ticket.getType() == null) {
            errors.put("type", MISSING_PROPERTY_MSG);
        }
        if (ticket.getPriority() == null) {
            errors.put("priority", MISSING_PROPERTY_MSG);
        }

        if (ticket.getStatus() == null) {
            errors.put("status", MISSING_PROPERTY_MSG);
        }

        final Long projectId = ticket.getProject().getId();
        if (projectId == null) {
            errors.put("project", MISSING_PROPERTY_MSG);
        } else {
            boolean exists = projectRepository.existsById(projectId);
            if (!exists) {
                errors.put("project", INVALID_PROJECT_MSG);
            }
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

    /**
     * Validates a ticket before the updated state is saved to the database.
     *
     * @param ticket ticket to be updated
     * @throws ConstraintsViolationException if any constraint is violated
     */
    public void validateTicketBeforeUpdate(Ticket ticket) throws ConstraintsViolationException {
        Assert.notNull(ticket, "ticket must not be null");
        final Map<String, String> errors = new HashMap<>();
        if (ticket.getProject().getId() == null) {
            errors.put("projectId", MISSING_PROPERTY_MSG);
        } else {
            boolean ticketExistsByIdAndProjectId = ticketRepository.existsByIdAndProjectId(ticket.getId(), ticket.getProject().getId());
            if (!ticketExistsByIdAndProjectId) {
                errors.put("projectId", INVALID_PROJECT_ID_MSG);
            }
        }

        try {
            validateTicket(ticket);
        } catch (ConstraintsViolationException ex) {
            errors.putAll(ex.getErrors());
        }

        if (!errors.isEmpty()) {
            throw new ConstraintsViolationException(errors);
        }
    }

}
