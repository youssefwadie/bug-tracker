package com.github.youssefwadie.bugtracker.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

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
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Long projectId;

    @JoinColumn(name = "assigned_developer_id", referencedColumnName = "id", nullable = true)
    private Long assignedDeveloperId;

    @JoinColumn(name = "submitter_id", referencedColumnName = "id", nullable = false)
    private Long submitterId;

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


