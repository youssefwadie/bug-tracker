package com.github.youssefwadie.bugtracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {
    public Project(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany
    @JoinTable(name = "works_on",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Collection<User> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "projectId", fetch = FetchType.LAZY)
    private Collection<Ticket> tickets = new ArrayList<>();
}
