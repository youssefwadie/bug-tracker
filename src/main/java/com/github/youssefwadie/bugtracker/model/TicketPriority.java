package com.github.youssefwadie.bugtracker.model;

import lombok.Getter;


@Getter
public enum TicketPriority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");
    private final String description;

    TicketPriority(String description) {
        this.description = description;
    }
}
