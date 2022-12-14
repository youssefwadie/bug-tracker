package com.github.youssefwadie.bugtracker.confirmationtoken.dao;


import com.github.youssefwadie.bugtracker.model.ConfirmationToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfirmationTokenRowMapper implements RowMapper<ConfirmationToken> {
    @Override
    public ConfirmationToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ConfirmationToken(rs.getLong("id"),
                rs.getString("token"),
                rs.getBoolean("confirmed"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("expired_at").toLocalDateTime(),
                rs.getLong("user_id"));
    }
}
