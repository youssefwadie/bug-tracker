package com.github.youssefwadie.bugtracker.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.youssefwadie.bugtracker.dto.annotations.JsonLocalDateTime;
import com.github.youssefwadie.bugtracker.model.TicketPriority;
import com.github.youssefwadie.bugtracker.model.TicketStatus;
import com.github.youssefwadie.bugtracker.model.TicketType;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TicketDto {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String title;
    private String description;
    private Long projectId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String projectName;
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
