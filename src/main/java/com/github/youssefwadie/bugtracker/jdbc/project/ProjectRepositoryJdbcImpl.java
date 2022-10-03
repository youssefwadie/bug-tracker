package com.github.youssefwadie.bugtracker.jdbc.project;

import com.github.youssefwadie.bugtracker.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.util.Streamable;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProjectRepositoryJdbcImpl implements ProjectRepositoryJdbc {
    private static final String INSERT_PROJECT_TEMPLATE = "INSERT INTO projects (name, description)" +
            "VALUES(?, ?)";

    private static final String UPDATE_PROJECT_TEMPLATE = "UPDATE projects SET name = ?, description = ? WHERE id = ?";
    private static final String QUERY_FIND_BY_ID_TEMPLATE = "SELECT * FROM projects WHERE id = ?";

    public static final String QUERY_CHECK_IF_EXISTS_BY_ID_TEMPLATE = "SELECT COUNT(*) > 0 FROM projects WHERE id = ?";
    private static final String QUERY_FIND_ALL = "SELECT * FROM projects";

    private static final String QUERY_COUNT_ALL = "SELECT COUNT(*) FROM projects";
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Project> rowMapper = new ProjectRowMapper();


    @Override
    @Modifying
    @Transactional
    public Project save(Project project) {
        Assert.notNull(project, "Todo must not be null!");
        if (project.getId() != null) {
            jdbcTemplate.update(UPDATE_PROJECT_TEMPLATE, project.getName(), project.getDescription(), project.getId());
            return project;
        }
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(INSERT_PROJECT_TEMPLATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            return preparedStatement;
        }, keyHolder);

        Long updatedTodoId = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : project.getId();
        return findById(updatedTodoId).orElseThrow(() -> new IncorrectResultSetColumnCountException(1, 0));
    }

    @Override
    @Transactional
    public Iterable<Project> saveAll(Iterable<Project> projects) {
        Assert.notNull(projects, "projects must not be null!");
        return Streamable.of(projects)
                .stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findById(Long id) {
        Assert.notNull(id, "id must not be null!");
        Project project = jdbcTemplate.queryForObject(QUERY_FIND_BY_ID_TEMPLATE, rowMapper, id);
        if (project == null) return Optional.empty();
        return Optional.of(project);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        Assert.notNull(id, "Id must not be null!");
        Boolean exists = jdbcTemplate.queryForObject(QUERY_CHECK_IF_EXISTS_BY_ID_TEMPLATE, Boolean.class, id);
        return exists != null ? exists : false;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Project> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, rowMapper);
    }

    @Override
    public Iterable<Project> findAllById(Iterable<Long> ids) {
        Assert.notNull(ids, "ids must not be null!");
        List<Project> projects = new ArrayList<>();
        ids.forEach(id -> findById(id).ifPresent(projects::add));
        return projects;
    }

    @Override
    public long count() {
        Long projectsCount = jdbcTemplate.queryForObject(QUERY_COUNT_ALL, Long.class);
        return projectsCount == null ? 0 : projectsCount;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(Project project) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {

    }

    @Override
    public void deleteAll(Iterable<Project> projects) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Iterable<Project> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Project> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Project> findByIdFetchTeamMembers(Long id) {
        return Optional.empty();
    }

    @Override
    public long countTeamMembersByProjectId(Long projectId) {
        return 0;
    }
}
