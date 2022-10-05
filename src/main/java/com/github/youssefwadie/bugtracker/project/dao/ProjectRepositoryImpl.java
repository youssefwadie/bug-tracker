package com.github.youssefwadie.bugtracker.project.dao;

import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.user.dao.UserRepository;
import com.github.youssefwadie.bugtracker.util.JdbcUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository {
    private static final String INSERT_PROJECT_TEMPLATE = "INSERT INTO projects (name, description)" +
            "VALUES(?, ?)";
    private static final String INSERT_ADD_USER_TO_PROJECT_TEAM_MEMBERS_TEMPLATE = "INSERT INTO works_on (`project_id`,`user_id`) VALUES (?, ?)";

    private static final String UPDATE_PROJECT_TEMPLATE = "UPDATE projects SET name = ?, description = ? WHERE id = ?";
    private static final String QUERY_FIND_BY_ID_TEMPLATE = "SELECT * FROM projects WHERE id = ?";

    public static final String QUERY_CHECK_IF_EXISTS_BY_ID_TEMPLATE = "SELECT COUNT(*) > 0 FROM projects WHERE id = ?";
    private static final String QUERY_FIND_ALL = "SELECT * FROM projects";

    private static final String QUERY_FIND_ALL_WITH_SORT_TEMPLATE = "SELECT * FROM projects ORDER BY %s";

    private static final String QUERY_FIND_ALL_WITH_SORT_AND_LIMIT_OFFSET_TEMPLATE = "SELECT * FROM projects ORDER BY %s LIMIT ? OFFSET ?";
    private static final String QUERY_FIND_ALL_WITH_LIMIT_OFFSET_TEMPLATE = "SELECT * FROM projects LIMIT ? OFFSET ?";

    private static final String QUERY_FIND_ALL_TEAM_MEMBERS_IDS_BY_PROJECT_ID_TEMPLATE = "SELECT wo.user_id FROM works_on wo WHERE wo.project_id = ? ORDER BY wo.user_id ASC";

    private static final String QUERY_COUNT_ALL = "SELECT COUNT(*) FROM projects";

    private static final String DELETE_BY_ID_TEMPLATE = "DELETE FROM projects WHERE id = ?";
    private static final String DELETE_TEAM_MEMBER_BY_ID_TEMPLATE = "DELETE FROM works_on WHERE project_id = ? AND user_id = ?";
    private static final String DELETE_ALL = "DELETE FROM projects";

    public static final String QUERY_CHECK_IF_USER_WORKS_ON_PROJECT_BY_ID_TEMPLATE = "SELECT COUNT(*) > 0 FROM works_on WHERE user_id = ? AND project_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final RowMapper<Project> rowMapper = new ProjectRowMapper();


    @Override
    @Transactional
    public Project save(Project project) {
        Assert.notNull(project, "Todo must not be null!");
        if (project.getId() != null) {
            jdbcTemplate.update(UPDATE_PROJECT_TEMPLATE, project.getName(), project.getDescription(), project.getId());
            return findById(project.getId()).orElseThrow(() -> new IncorrectResultSetColumnCountException(1, 0));
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
    public List<Project> findAll() {
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
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, "id must not be null!");
        jdbcTemplate.update(DELETE_BY_ID_TEMPLATE, id);
    }

    @Override
    @Transactional
    public void delete(Project project) {
        Assert.notNull(project, "project must not be null!");
        deleteById(project.getId());
    }

    @Override
    @Transactional
    public void deleteAllById(Iterable<? extends Long> ids) {
        Assert.notNull(ids, "IDs must not be null!");
        ids.forEach(this::deleteById);
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<Project> projects) {
        Assert.notNull(projects, "project must not be null!");
        projects.forEach(this::delete);
    }

    @Override
    @Transactional
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Project> findAll(Sort sort) {
        Assert.notNull(sort, "sort must not be null!");
        if (sort.isUnsorted()) {
            return this.findAll();
        }
        Stream<Sort.Order> orderStream = sort.get();
        String sortString = JdbcUtils.buildSortString(orderStream);
        return jdbcTemplate.query(String.format(QUERY_FIND_ALL_WITH_SORT_TEMPLATE, sortString), rowMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Project> findAll(Pageable pageable) {
        Assert.notNull(pageable, "pageable cannot be null");
        final long count = count();

        if (pageable.isUnpaged()) {
            return new PageImpl<>(findAll(), pageable, count);
        }

        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            List<Project> projects =
                    jdbcTemplate.query(QUERY_FIND_ALL_WITH_LIMIT_OFFSET_TEMPLATE, rowMapper, pageable.getPageSize(), pageable.getOffset());
            return new PageImpl<>(projects, pageable, count);
        }

        String sortString = JdbcUtils.buildSortString(sort.get());
        List<Project> projects =
                jdbcTemplate.query(String.format(QUERY_FIND_ALL_WITH_SORT_AND_LIMIT_OFFSET_TEMPLATE, sortString),
                        rowMapper, pageable.getPageSize(), pageable.getOffset());
        return new PageImpl<>(projects, pageable, count);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findByIdFetchTeamMembers(Long id, Pageable pageable) {
        Optional<Project> project = findById(id);
        project.ifPresent(p -> {
            Page<User> users = userRepository.findAllTeamMembers(id, pageable);
            p.setTeamMembers(Set.copyOf(users.getContent()));
        });
        return project;
    }

    @Override
    @Transactional
    public void addUserToProjectTeamMembers(Long newTeamMember, Long projectId) {
        Assert.notNull(newTeamMember, "newTeamMember must not be null!");
        Assert.notNull(projectId, "projectId must not be null!");
        if (!existsById(projectId)) {
            throw new IllegalArgumentException("projectId doesn't exist");
        }
        if (!userRepository.existsById(newTeamMember)) {
            throw new IllegalArgumentException("userId doesn't exist");
        }

        if (!userWorksOnProject(newTeamMember, projectId)) {
            jdbcTemplate.update(INSERT_ADD_USER_TO_PROJECT_TEAM_MEMBERS_TEMPLATE, projectId, newTeamMember);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userWorksOnProject(Long teamMemberId, Long projectId) {
        Assert.notNull(teamMemberId, "teamMemberId must not be null!");
        Assert.notNull(projectId, "projectId must not be null!");
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(QUERY_CHECK_IF_USER_WORKS_ON_PROJECT_BY_ID_TEMPLATE, Boolean.class, teamMemberId, projectId));
    }


    @Override
    @Transactional(readOnly = true)
    public List<Long> getTeamMemberIds(Long projectId) {
        return jdbcTemplate.queryForList(QUERY_FIND_ALL_TEAM_MEMBERS_IDS_BY_PROJECT_ID_TEMPLATE, Long.class, projectId);
    }

    @Override
    @Transactional
    public void removeUserFromProjectTeamMembers(Long teamMemberId, Long projectId) {
        Assert.notNull(teamMemberId, "teamMemberId must not be null!");
        Assert.notNull(projectId, "projectId must not be null!");
        jdbcTemplate.update(DELETE_TEAM_MEMBER_BY_ID_TEMPLATE, projectId, teamMemberId);
    }
}
