package com.github.youssefwadie.bugtracker.user.dao;

import com.github.youssefwadie.bugtracker.model.Role;
import com.github.youssefwadie.bugtracker.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    private final String idColumnName;
    private final String emailColumnName;
    private final String passwordColumnName;
    private final String firstNameColumnName;
    private final String lastNameColumnName;

    private final String roleColumnName;
    private final String emailVerifiedColumnName;

    public UserRowMapper(String prefix) {
        this.idColumnName = prefix + "id";
        this.emailColumnName = prefix + "email";
        this.passwordColumnName = prefix + "password";
        this.firstNameColumnName = prefix + "first_name";
        this.lastNameColumnName = prefix + "last_name";
        this.roleColumnName = prefix + "role";
        this.emailVerifiedColumnName = prefix + "email_verified";
    }

    public UserRowMapper() {
        this("");
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Long id = rs.getLong(idColumnName);
        final String email = rs.getString(emailColumnName);
        final String password = rs.getString(passwordColumnName);
        final String firstName = rs.getString(firstNameColumnName);
        final String lastName = rs.getString(lastNameColumnName);
        final Role role = Role.valueOf(rs.getString(roleColumnName));
        final boolean emailVerified = rs.getBoolean(emailVerifiedColumnName);
        return new User(id, email, password, firstName, lastName, role, emailVerified);
    }
}
