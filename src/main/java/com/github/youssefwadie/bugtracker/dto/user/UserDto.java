package com.github.youssefwadie.bugtracker.dto.user;

import com.github.youssefwadie.bugtracker.model.Role;
import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String fullName;
    private Role role;
}
