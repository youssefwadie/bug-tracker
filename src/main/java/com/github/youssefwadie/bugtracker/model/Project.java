package com.github.youssefwadie.bugtracker.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
    @ManyToMany
    @JoinTable(name = "works_on",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Collection<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private Collection<Ticket> tickets = new ArrayList<>();
}
