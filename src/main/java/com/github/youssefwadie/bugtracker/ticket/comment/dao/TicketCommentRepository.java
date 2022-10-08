package com.github.youssefwadie.bugtracker.ticket.comment.dao;


import com.github.youssefwadie.bugtracker.model.TicketComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TicketCommentRepository {
    /**
     * Saves a given comment. Use the returned instance for further operations as the save operation might have changed the
     * comment instance completely.
     *
     * @param comment must not be {@literal null}.
     * @return the saved comment; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal comment} is {@literal null}.
     */
    TicketComment save(TicketComment comment);


    /**
     * Retrieves a {@link TicketComment comment} by its id.
     *
     * @param id must not be {@literal null}.
     * @return the comment with the given id or {@literal  Optional#empty()} if non found.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}.
     */
    Optional<TicketComment> findById(Long id);


    /**
     * Returns the number of comments available.
     *
     * @return the number of comments.
     */
    long count();

    /**
     * Deletes the comment with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    void deleteById(Long id);

    /**
     * Deletes the given comment.
     *
     * @param comment must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal comment} is {@literal null} or {@literal not null} but {@literal the comment's} {@literal null}
     */
    void delete(TicketComment comment);



    /**
     * Returns whether comment with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if a user with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    boolean existsById(Long id);



    /**
     * Returns a {@link Page} of comments meeting the paging restriction provided in the {@link Pageable} object.
     *
     * @param ticketId must not be {@literal null}.
     * @param pageable  the pageable to request a paged result, can be {@link Pageable#unpaged()}, must not be
     *                  {@literal null}.
     * @return a page of users
     * @throws IllegalArgumentException if the given {@code ticketId} is null
     */
    Page<TicketComment> findAllByTicket(Long ticketId, Pageable pageable);

    /**
     * Returns all the comments of a given ticket by its id
     * @param ticketId must not be {@literal null}.
     * @return all comments.
     */
    List<TicketComment> findAllByTicket(Long ticketId);

    /**
     * Returns the number of comments for a given project's id.
     *
     * @return the number of comments.
     */
    long countByTicket(Long projectId);

    /**
     * Returns whether comment with the given id is created by a user with a given id.
     * @param commentId must not be {@literal null}.
     * @param userId must not be {@literal null}.
     * @return {@literal true} if the comment is created by user, {@literal false} otherwise.
     */
    boolean createdByUser(Long commentId, Long userId);
}
