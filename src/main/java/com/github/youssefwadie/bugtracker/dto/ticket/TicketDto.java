package com.github.youssefwadie.bugtracker.dto.ticket;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.youssefwadie.bugtracker.dto.annotations.JsonLocalDateTime;
import com.github.youssefwadie.bugtracker.model.TicketPriority;
import com.github.youssefwadie.bugtracker.model.TicketStatus;
import com.github.youssefwadie.bugtracker.model.TicketType;

import lombok.Data;


@Data
public class TicketDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String title;
    private String description;
    private Long project;
    private Long assignedDeveloper;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long submitter;
    private TicketType type;
    private TicketPriority priority;
    private TicketStatus status;
    
    @JsonLocalDateTime
    private LocalDateTime createdAt;

    @JsonLocalDateTime
    private LocalDateTime updatedAt;

}
