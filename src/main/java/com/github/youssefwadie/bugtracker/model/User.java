package com.github.youssefwadie.bugtracker.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", nullable = true)
    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;

    private String email;

    private String password;

    private boolean enabled;
}
