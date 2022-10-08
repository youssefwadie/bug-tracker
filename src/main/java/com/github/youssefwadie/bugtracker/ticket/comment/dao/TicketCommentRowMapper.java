package com.github.youssefwadie.bugtracker.ticket.comment.dao;

import com.github.youssefwadie.bugtracker.model.TicketComment;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.user.dao.UserRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class TicketCommentRowMapper implements RowMapper<TicketComment> {

    private final RowMapper<User> userRowMapper = new UserRowMapper("user_");

    @Override
    public TicketComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        TicketComment comment = new TicketComment();
        User commenter = userRowMapper.mapRow(rs, rowNum);

        comment.setId(rs.getLong("id"));
        comment.setContent(rs.getString("content"));
        comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        final Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            comment.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        comment.setCommenter(commenter);
        comment.setTicketId(rs.getLong("ticket_id"));
        return comment;
    }
}
