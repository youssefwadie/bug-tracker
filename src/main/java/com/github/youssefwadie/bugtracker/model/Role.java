package com.github.youssefwadie.bugtracker.model;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN_ROLE("Admin"),
    DEVELOPER_ROLE("Developer");

    private final String description;

    Role(String description) {
        this.description = description;
    }

}
