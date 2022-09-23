package com.github.youssefwadie.bugtracker.dto.ticket;

import com.github.youssefwadie.bugtracker.model.TicketPriority;
import com.github.youssefwadie.bugtracker.model.TicketStatus;
import com.github.youssefwadie.bugtracker.model.TicketType;
import lombok.Data;

import java.time.LocalDate;


@Data
public class TicketDto {
    private String title;
    private String description;
    private String project;
    private String assignedDeveloper;
    private TicketType type;
    private TicketPriority priority;
    private TicketStatus status;
    private LocalDate createdAt;

}
