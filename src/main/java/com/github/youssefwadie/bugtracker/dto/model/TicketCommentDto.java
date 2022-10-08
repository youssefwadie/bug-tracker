package com.github.youssefwadie.bugtracker.dto.model;


import com.github.youssefwadie.bugtracker.dto.annotations.JsonLocalDateTime;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketCommentDto {
    private Long id;
    private String content;

    @JsonLocalDateTime
    private LocalDateTime createdAt;

    @JsonLocalDateTime
    private LocalDateTime updatedAt;

    private Long ticketId;
    private UserDto commenter;
}
