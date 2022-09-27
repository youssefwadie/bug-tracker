package com.github.youssefwadie.bugtracker.ticket;

public class TicketNotFoundException extends IllegalArgumentException {
    public TicketNotFoundException(String message) {
        super(message);
    }

    public TicketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
