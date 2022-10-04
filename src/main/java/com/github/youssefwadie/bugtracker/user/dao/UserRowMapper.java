package com.github.youssefwadie.bugtracker.user.dao;

import com.github.youssefwadie.bugtracker.model.Role;
import com.github.youssefwadie.bugtracker.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements org.springframework.jdbc.core.RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Long id = rs.getLong("id");
        final String email = rs.getString("email");
        final String firstName = rs.getString("first_name");
        final String lastName = rs.getString("last_name");
        final String password = rs.getString("password");
        final Role role = Role.valueOf(rs.getString("role"));
        final boolean emailVerified = rs.getBoolean("email_verified");
        return new User(id, email, password, firstName, lastName, role, emailVerified);
    }
}
