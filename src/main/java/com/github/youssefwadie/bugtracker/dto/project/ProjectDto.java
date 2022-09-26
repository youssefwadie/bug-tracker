package com.github.youssefwadie.bugtracker.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.youssefwadie.bugtracker.dto.user.UserDto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String name;
    private String description;

    private List<UserDto> teamMembers;
}
