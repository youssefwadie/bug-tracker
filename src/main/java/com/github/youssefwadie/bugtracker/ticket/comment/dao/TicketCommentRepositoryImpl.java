package com.github.youssefwadie.bugtracker.ticket.comment.dao;

import com.github.youssefwadie.bugtracker.model.TicketComment;
import com.github.youssefwadie.bugtracker.util.JdbcUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TicketCommentRepositoryImpl implements TicketCommentRepository {
    private static final String INSERT_NEW_COMMENT_TEMPLATE = "INSERT INTO ticket_comments(content, commenter_id, ticket_id, created_at) VALUES (?, ?, ?, NOW())";
    private static final String UPDATE_COMMENT_TEMPLATE = "UPDATE ticket_comments SET content = ?, updated_at = NOW() WHERE id = ?";

    private static final String QUERY_FIND_BY_ID_TEMPLATE = """
            SELECT tc.*,
            u.id AS user_id,
            u.email AS user_email,
            u.password AS user_password,
            u.first_name AS user_first_name,
            u.last_name AS user_last_name,
            u.role AS user_role,
            u.email_verified AS user_email_verified
            FROM ticket_comments tc INNER JOIN users u on tc.commenter_id = u.id WHERE tc.id = ?""";

    private static final String QUERY_COUNT_ALL = "SELECT COUNT(*) FROM ticket_comments";

    private static final String QUERY_FIND_ALL_BY_TICKET_ID_TEMPLATE = """
            SELECT tc.*,
            u.id AS user_id,
            u.email AS user_email,
            u.password AS user_password,
            u.first_name AS user_first_name,
            u.last_name AS user_last_name,
            u.role AS user_role,
            u.email_verified AS user_email_verified
            FROM ticket_comments tc INNER JOIN users u on tc.commenter_id = u.id WHERE tc.ticket_id = ?""";

    private static final String QUERY_CHECK_IF_EXISTS_BY_ID_TEMPLATE = "SELECT COUNT(*) > 0 FROM ticket_comments WHERE id = ?";

    private static final String QUERY_CHECK_IF_EXISTS_BY_ID_AND_USER_ID_TEMPLATE = "SELECT COUNT(*) > 0 FROM ticket_comments WHERE id = ? AND commenter_id = ?";

    private static final String DELETE_BY_ID_TEMPLATE = "DELETE FROM ticket_comments WHERE id = ?";
    private static final String QUERY_FIND_ALL_BY_TICKET_ID_WITH_LIMIT_OFFSET_TEMPLATE = """
            SELECT tc.*,
            u.id AS user_id,
            u.email AS user_email,
            u.password AS user_password,
            u.first_name AS user_first_name,
            u.last_name AS user_last_name,
            u.role AS user_role,
            u.email_verified AS user_email_verified
            FROM ticket_comments tc INNER JOIN users u on tc.commenter_id = u.id WHERE tc.ticket_id = ? LIMIT ? OFFSET ?""";

    private static final String QUERY_FIND_ALL_BY_TICKET_ID_WITH_SORT_AND_LIMIT_OFFSET_TEMPLATE = """
            SELECT tc.*,
            u.id AS user_id,
            u.email AS user_email,
            u.password AS user_password,
            u.first_name AS user_first_name,
            u.last_name AS user_last_name,
            u.role AS user_role,
            u.email_verified AS user_email_verified
            FROM ticket_comments tc INNER JOIN users u on tc.commenter_id = u.id WHERE tc.ticket_id = ? ORDER BY %s LIMIT ? OFFSET ?""";

    private static final String QUERY_COUNT_ALL_BY_PROJECT_ID_TEMPLATE = "SELECT COUNT(*) FROM ticket_comments WHERE ticket_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TicketComment> rowMapper = new TicketCommentRowMapper();


    @Override
    @Transactional
    public TicketComment save(TicketComment comment) {
        Assert.notNull(comment, "comment must not be null!");
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        if (comment.getId() != null && existsById(comment.getId())) {
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(UPDATE_COMMENT_TEMPLATE);
                preparedStatement.setString(1, comment.getContent());
                preparedStatement.setLong(2, comment.getId());
                return preparedStatement;
            });
        } else {
            Assert.notNull(comment.getCommenter(), "comment.commenter must not be null!");
            Assert.notNull(comment.getCommenter().getId(), "comment.commenter.id must not be null!");
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(INSERT_NEW_COMMENT_TEMPLATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, comment.getContent());
                preparedStatement.setLong(2, comment.getCommenter().getId());
                preparedStatement.setLong(3, comment.getTicketId());
                return preparedStatement;
            }, keyHolder);
        }

        Long updatedCommentId = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : comment.getId();
        return findById(updatedCommentId).orElseThrow(() -> new IncorrectResultSetColumnCountException(1, 0));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketComment> findById(Long id) {
        Assert.notNull(id, "must not be null!");
        try {
            TicketComment comment = jdbcTemplate.queryForObject(QUERY_FIND_BY_ID_TEMPLATE, rowMapper, id);
            if (comment == null) return Optional.empty();
            return Optional.of(comment);
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        Long commentsCount = jdbcTemplate.queryForObject(QUERY_COUNT_ALL, Long.class);
        return commentsCount == null ? 0 : commentsCount;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, "id must not be null!");
        jdbcTemplate.update(DELETE_BY_ID_TEMPLATE, id);
    }

    @Override
    @Transactional
    public void delete(TicketComment comment) {
        Assert.notNull(comment, "comment must not be null!");
        Assert.notNull(comment.getId(), "comment.id must not be null!");
        jdbcTemplate.update(DELETE_BY_ID_TEMPLATE, comment.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        Assert.notNull(id, "id must not be null!");
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(QUERY_CHECK_IF_EXISTS_BY_ID_TEMPLATE, Boolean.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketComment> findAllByTicket(Long ticketId) {
        Assert.notNull(ticketId, "ticketId must not be null!");
        return jdbcTemplate.query(QUERY_FIND_ALL_BY_TICKET_ID_TEMPLATE, rowMapper, ticketId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketComment> findAllByTicket(Long ticketId, Pageable pageable) {
        Assert.notNull(ticketId, "ticketId must not be null");
        Assert.notNull(pageable, "pageable must not be null");

        final long count = countByTicket(ticketId);
        if (pageable.isUnpaged()) {
            return new PageImpl<>(findAllByTicket(ticketId), pageable, count);
        }

        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            List<TicketComment> comments =
                    jdbcTemplate
                            .query(QUERY_FIND_ALL_BY_TICKET_ID_WITH_LIMIT_OFFSET_TEMPLATE, rowMapper,
                                    ticketId, pageable.getPageSize(),
                                    pageable.getOffset());
            return new PageImpl<>(comments, pageable, count);
        }

        String sortString = JdbcUtils.buildSortString(sort.get());
        List<TicketComment> comments =
                jdbcTemplate.query(String.format(QUERY_FIND_ALL_BY_TICKET_ID_WITH_SORT_AND_LIMIT_OFFSET_TEMPLATE, sortString),
                        rowMapper, pageable.getPageSize(), pageable.getOffset());
        return new PageImpl<>(comments, pageable, count);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByTicket(Long projectId) {
        Assert.notNull(projectId, "projectId must not be null!");
        Long ticketCommentsCount = jdbcTemplate.queryForObject(QUERY_COUNT_ALL_BY_PROJECT_ID_TEMPLATE, Long.class, projectId);
        return ticketCommentsCount == null ? 0 : ticketCommentsCount;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean createdByUser(Long commentId, Long userId) {
        Assert.notNull(commentId, "commentId must not be null!");
        Assert.notNull(userId, "userId must not be null!");
        Boolean createdByUser = jdbcTemplate.queryForObject(QUERY_CHECK_IF_EXISTS_BY_ID_AND_USER_ID_TEMPLATE, Boolean.class, commentId, userId);
        return Boolean.TRUE.equals(createdByUser);
    }
}
