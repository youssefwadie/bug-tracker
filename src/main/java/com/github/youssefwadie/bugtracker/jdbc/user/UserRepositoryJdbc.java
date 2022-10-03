package com.github.youssefwadie.bugtracker.jdbc.user;

import com.github.youssefwadie.bugtracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryJdbc {

    /**
     * Returns a {@link Page} of users meeting the paging restriction provided in the {@link Pageable} object.
     *
     * @param projectId id must not be {@literal null}.
     * @param pageable  the pageable to request a paged result, can be {@link Pageable#unpaged()}, must not be
     *                  {@literal null}.
     * @return a page of users
     * @throws IllegalArgumentException if the given {@code projectId} is null
     */
    Page<User> findAllTeamMembers(Long projectId, Pageable pageable);

}
