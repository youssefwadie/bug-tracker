package com.github.youssefwadie.bugtracker.model;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_DEVELOPER("Developer");

    private final String description;

    Role(String description) {
        this.description = description;
    }

}
