package com.github.youssefwadie.bugtracker.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id", nullable = true)
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "submitter_id", referencedColumnName = "id", nullable = false)
    private User submitter;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TicketType type;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}


