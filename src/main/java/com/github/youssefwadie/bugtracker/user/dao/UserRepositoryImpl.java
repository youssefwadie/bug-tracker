package com.github.youssefwadie.bugtracker.user.dao;

import com.github.youssefwadie.bugtracker.util.JdbcUtils;
import com.github.youssefwadie.bugtracker.model.User;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    public static final String INSERT_NEW_USER_TEMPLATE = "INSERT INTO users (email, password, first_name, last_name, role, email_verified) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_USER_TEMPLATE = "UPDATE users SET email = ?, password = ?, first_name = ?, last_name = ?, role = ?, email_verified = ? WHERE id = ?";

    public static final String QUERY_FIND_BY_ID_TEMPLATE = "SELECT * FROM users WHERE id = ?";
    public static final String QUERY_FIND_BY_EMAIL_TEMPLATE = "SELECT * FROM users WHERE email = ?";
    public static final String QUERY_CHECK_IF_EXISTS_BY_EMAIL_TEMPLATE = "SELECT COUNT(*) > 0 FROM users WHERE email = ?";
    public static final String QUERY_CHECK_IF_EXISTS_BY_ID_TEMPLATE = "SELECT COUNT(*) > 0 FROM users WHERE id = ?";


    public static final String QUERY_COUNT_ALL = "SELECT COUNT(*) FROM users";

    public static final String QUERY_FIND_ALL = "SELECT * FROM users";

    public static final String DELETE_BY_ID_TEMPLATE = "DELETE FROM users WHERE id = ?";

    public static final String DELETE_ALL = "DELETE FROM users";
    private static final String ENABLE_USER_BY_ID_TEMPLATE = "UPDATE users SET email_verified = ? WHERE id = ?";

    private static final String QUERY_FIND_ALL_WITH_SORT_AND_LIMIT_OFFSET_TEMPLATE = "SELECT u.* FROM users u JOIN works_on wo on u.id = wo.user_id WHERE wo.project_id = ? ORDER BY %s LIMIT ? OFFSET ?";
    private static final String QUERY_FIND_ALL_WITH_LIMIT_OFFSET_TEMPLATE = "SELECT u.* FROM users u JOIN works_on wo on u.id = wo.user_id WHERE wo.project_id = ? LIMIT ? OFFSET ?";
    private static final String QUERY_FIND_ALL_BY_PROJECT_ID_TEMPLATE = "SELECT u.* FROM users u JOIN works_on wo on u.id = wo.user_id WHERE wo.project_id = ?";
    private static final String QUERY_COUNT_TEAM_MEMBERS_BY_PROJECT_ID = "SELECT COUNT(*) FROM works_on WHERE project_id = ?";


    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = new UserRowMapper();
    }

    @Override
    @Transactional
    public User save(User user) {
        Assert.notNull(user, "user must not be null!");
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        if (user.getId() != null && existsById(user.getId())) {
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(UPDATE_USER_TEMPLATE);
                setPreparedStatementDetails(user, preparedStatement);
                preparedStatement.setLong(7, user.getId());
                return preparedStatement;
            });
        } else {
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(INSERT_NEW_USER_TEMPLATE, Statement.RETURN_GENERATED_KEYS);
                setPreparedStatementDetails(user, preparedStatement);
                return preparedStatement;
            }, keyHolder);
        }
        Long updatedUserId = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : user.getId();
        return findById(updatedUserId).orElseThrow(() -> new IncorrectResultSetColumnCountException(1, 0));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        Assert.notNull(email, "Email must not be null!");
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(QUERY_CHECK_IF_EXISTS_BY_EMAIL_TEMPLATE, Boolean.class, email));
    }

    @Override
    @Transactional
    public void enableUserById(Long userId) {
        Assert.notNull(userId, "userId must not be null!");
        jdbcTemplate.update(ENABLE_USER_BY_ID_TEMPLATE, true, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        Assert.notNull(email, "email must not be null!");
        User user = jdbcTemplate.queryForObject(QUERY_FIND_BY_EMAIL_TEMPLATE, rowMapper, email);
        if (user == null) return Optional.empty();
        return Optional.of(user);
    }


    @Override
    @Transactional
    public Iterable<User> saveAll(Iterable<User> users) {
        Assert.notNull(users, "users must not be null!");
        return Streamable.of(users)
                .stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        Assert.notNull(id, "id must not be null!");
        User user = jdbcTemplate.queryForObject(QUERY_FIND_BY_ID_TEMPLATE, rowMapper, id);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        Assert.notNull(id, "id must not be null!");
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(QUERY_CHECK_IF_EXISTS_BY_ID_TEMPLATE, Boolean.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, rowMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAllById(Iterable<Long> ids) {
        Assert.notNull(ids, "IDs must not be null");
        List<User> users = new LinkedList<>();
        ids.forEach(id -> findById(id).ifPresent(users::add));
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        Long usersCount = jdbcTemplate.queryForObject(QUERY_COUNT_ALL, Long.class);
        return usersCount == null ? 0 : usersCount;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, "Id must not be null!");
        jdbcTemplate.update(DELETE_BY_ID_TEMPLATE, id);
    }

    @Override
    @Transactional
    public void delete(User user) {
        Assert.notNull(user, "User must not be null!");
        this.deleteById(user.getId());
    }

    @Override
    @Transactional
    public void deleteAllById(Iterable<? extends Long> ids) {
        Assert.notNull(ids, "ids must not be null!");
        ids.forEach(this::deleteById);
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends User> users) {
        Assert.notNull(users, "users must not be null!");
        users.forEach(this::delete);
    }

    @Override
    @Transactional
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllTeamMembers(Long projectId, Pageable pageable) {
        Assert.notNull(projectId, "projectId must not be null!");
        Assert.notNull(pageable, "pageable must not be null!");

        if (pageable.isUnpaged()) {
            return new PageImpl<>(findAllByProjectId(projectId));
        }

        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            List<User> users =
                    jdbcTemplate.query(QUERY_FIND_ALL_WITH_LIMIT_OFFSET_TEMPLATE, rowMapper, projectId, pageable.getPageSize(), pageable.getOffset());
            return new PageImpl<>(users);
        }

        String sortString = JdbcUtils.buildSortString(sort.get());
        List<User> users =
                jdbcTemplate.query(String.format(QUERY_FIND_ALL_WITH_SORT_AND_LIMIT_OFFSET_TEMPLATE, sortString),
                        rowMapper, projectId, pageable.getPageSize(), pageable.getOffset());
        return new PageImpl<>(users);
    }

    @Override
    @Transactional(readOnly = true)
    public long countTeamMembersByProjectId(Long projectId) {
        Long teamMembersCount = jdbcTemplate.queryForObject(QUERY_COUNT_TEAM_MEMBERS_BY_PROJECT_ID, Long.class, projectId);
        return teamMembersCount == null ? 0 : teamMembersCount;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByProjectId(Long projectId) {
        return jdbcTemplate.query(QUERY_FIND_ALL_BY_PROJECT_ID_TEMPLATE, rowMapper, projectId);
    }

    private void setPreparedStatementDetails(User user, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getRole().name());
        preparedStatement.setBoolean(6, user.isEmailVerified());
    }
}
