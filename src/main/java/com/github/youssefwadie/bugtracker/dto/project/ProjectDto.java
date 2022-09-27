package com.github.youssefwadie.bugtracker.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.youssefwadie.bugtracker.dto.ticket.TicketDto;
import com.github.youssefwadie.bugtracker.dto.user.UserDto;
import lombok.Data;

import java.util.Collection;

@Data
public class ProjectDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String name;
    private String description;
    private Collection<UserDto> teamMembers;
    private Collection<TicketDto> tickets;
}
