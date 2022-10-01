package com.github.youssefwadie.bugtracker.dto.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProjectDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String name;
    private String description;
    private Collection<UserDto> teamMembers;
    private Collection<TicketDto> tickets;
}
