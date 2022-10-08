package com.github.youssefwadie.bugtracker.ticket.comment.dao;


import com.github.youssefwadie.bugtracker.AbstractJdbcTest;
import com.github.youssefwadie.bugtracker.model.TicketComment;
import com.github.youssefwadie.bugtracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Rollback(value = false)
public class TicketCommentRepositoryTests extends AbstractJdbcTest {

    @Autowired
    TicketCommentRepository ticketCommentRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void testSaveNewComment() {
        final long ticketId = 1L;
        final long commenterId = 1L;

        final User commenter = new User(commenterId);
        final String content = "Comment 1";

        TicketComment comment = new TicketComment();
        comment.setContent(content);
        comment.setTicketId(ticketId);
        comment.setCommenter(commenter);

        TicketComment savedComment = ticketCommentRepository.save(comment);
        assertThat(savedComment.getId()).isGreaterThan(0L);
        assertThat(savedComment.getTicketId()).isEqualTo(ticketId);
        assertThat(savedComment.getCommenter().getId()).isEqualTo(commenterId);
        assertThat(savedComment.getContent()).isEqualTo(content);
        assertThat(savedComment.getCreatedAt()).isNotNull();
        assertThat(savedComment.getUpdatedAt()).isNull();
        assertThat(savedComment.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void testUpdateComment() {
        final Long lastInsertedId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM ticket_comments", Long.class);
        final long ticketId = lastInsertedId == null ? -1 : lastInsertedId;

        // no way to test for updates
        if (ticketId == -1) {
            return;
        }
        final long commentId = 1L;

        final String content = "Comment 1 edited";

        TicketComment comment = new TicketComment();
        comment.setId(commentId);
        comment.setContent(content);

        TicketComment savedComment = ticketCommentRepository.save(comment);
        assertThat(savedComment.getId()).isGreaterThan(0L);
        assertThat(savedComment.getTicketId()).isEqualTo(ticketId);
        assertThat(savedComment.getCommenter()).isNotNull();
        assertThat(savedComment.getCommenter().getId()).isGreaterThan(0);
        assertThat(savedComment.getContent()).isEqualTo(content);
        assertThat(savedComment.getUpdatedAt()).isNotNull();
        assertThat(savedComment.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }


}
