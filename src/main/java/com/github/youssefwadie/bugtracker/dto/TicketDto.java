package com.github.youssefwadie.bugtracker.dto;

import com.github.youssefwadie.bugtracker.model.TicketPriority;
import com.github.youssefwadie.bugtracker.model.TicketStatus;
import com.github.youssefwadie.bugtracker.model.TicketType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketDto {
    private String title;

    private String description;

    @NotNull
    private Long project;

    @NotNull
    private Long assignedTo;

    @NotNull
    private TicketType type;

    @NotNull
    private TicketPriority priority;

    @NotNull
    private TicketStatus status;

}
