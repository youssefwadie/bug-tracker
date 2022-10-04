package com.github.youssefwadie.bugtracker.project.dao;

import com.github.youssefwadie.bugtracker.model.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Project(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }
}
