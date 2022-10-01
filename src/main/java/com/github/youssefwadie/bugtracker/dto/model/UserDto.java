package com.github.youssefwadie.bugtracker.dto.model;

import com.github.youssefwadie.bugtracker.model.Role;

import lombok.Data;

@Data
public class UserDto {
	private Long id;
    private String email;
    private String fullName;
    private Role role;
}
