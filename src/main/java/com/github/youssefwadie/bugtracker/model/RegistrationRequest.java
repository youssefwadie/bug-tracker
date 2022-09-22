package com.github.youssefwadie.bugtracker.model;

import lombok.Data;


@Data
public class RegistrationRequest {
    private String email;
    private String password;

}
