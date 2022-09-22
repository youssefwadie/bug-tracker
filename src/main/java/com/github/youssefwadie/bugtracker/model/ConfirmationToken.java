package com.github.youssefwadie.bugtracker.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 36)
    private String token;
    private boolean confirmed;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "user_id")
    @JoinColumn(referencedColumnName = "id")
    private Long userId;

}
