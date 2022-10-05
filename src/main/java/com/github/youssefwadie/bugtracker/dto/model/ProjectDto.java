package com.github.youssefwadie.bugtracker.dto.model;

import lombok.Data;

import java.util.Collection;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private Collection<UserDto> teamMembers;
    private Collection<TicketDto> tickets;
}
