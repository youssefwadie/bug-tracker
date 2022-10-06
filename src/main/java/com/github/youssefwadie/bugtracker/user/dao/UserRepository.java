package com.github.youssefwadie.bugtracker.user.dao;

import com.github.youssefwadie.bugtracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    /**
     * Saves a given user. Use the returned instance for further operations as the save operation might have changed the
     * user instance completely.
     *
     * @param user must not be {@literal null}.
     * @return the saved user; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal user} is {@literal null}.
     */
    User save(User user);


    /**
     * Saves all given users.
     *
     * @param users must not be {@literal null} nor must it contain {@literal null}.
     * @return the saved users; will never be {@literal null}. The returned {@literal Iterable} will have the same size
     * as the {@literal Iterable} passed as an argument.
     * @throws IllegalArgumentException in case the given {@link Iterable users} or one of its users is
     *                                  {@literal null}.
     */
    Iterable<User> saveAll(Iterable<User> users);


    /**
     * Retrieves a {@link User user} by their id.
     *
     * @param id must not be {@literal null}.
     * @return the user with the given id or {@literal  Optional#empty()} if non found.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}.
     */
    Optional<User> findById(Long id);


    /**
     * Returns all users.
     *
     * @return all users
     */
    List<User> findAll();


    /**
     * Returns a {@link Page} of projects meeting the paging restriction provided in the {@link Pageable} object.
     *
     * @param pageable the pageable to request a paged result, can be {@link Pageable#unpaged()}, must not be
     *                 {@literal null}.
     * @return a page of users
     */
    Page<User> findAll(Pageable pageable);


    /**
     * Returns all {@link User users} with the given IDs.
     * <p>
     * If some or all ids are not found, no users are returned for these IDs.
     * <p>
     *
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
     * {@literal ids}.
     * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
     */

    Iterable<User> findAllById(Iterable<Long> ids);

    /**
     * Returns the number of users available.
     *
     * @return the number of users.
     */
    long count();

    /**
     * Deletes the user with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    void deleteById(Long id);

    /**
     * Deletes the given user.
     *
     * @param user must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal user} is {@literal null} or {@literal not null} but {@literal the user's} {@literal null}
     */
    void delete(User user);

    /**
     * Deletes all {@link User users} with the given IDs.
     *
     * @param ids must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal ids} or one of its users is {@literal null}.
     */

    void deleteAllById(Iterable<? extends Long> ids);


    /**
     * Deletes the given users.
     *
     * @param users must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal users} or one of the users is {@literal null}.
     */
    void deleteAll(Iterable<? extends User> users);

    /**
     * Deletes all users.
     */
    void deleteAll();


    /**
     * Returns whether user with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if a user with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    boolean existsById(Long id);
    /**
     * Retrieves a user by their email.
     *
     * @param email must not be {@literal null}.
     * @return the user with the given email or {@link Optional#empty()} if non found.
     * @throws IllegalArgumentException in case the given {@literal user} is {@literal null}.
     */
    Optional<User> findByEmail(String email);


    /**
     * Returns whether user with the given email exists.
     *
     * @param email must not be {@literal null}.
     * @return {@literal true} if a user with the given email exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    boolean existsByEmail(String email);

    /**
     * Enables the user with the given id
     * @param userId must not be {@literal null}.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    void enableUserById(Long userId);

    /**
     * Returns a {@link Page} of users meeting the paging restriction provided in the {@link Pageable} object.
     *
     * @param projectId must not be {@literal null}.
     * @param pageable  the pageable to request a paged result, can be {@link Pageable#unpaged()}, must not be
     *                  {@literal null}.
     * @return a page of users
     * @throws IllegalArgumentException if the given {@code projectId} is null
     */
    Page<User> findAllTeamMembers(Long projectId, Pageable pageable);
    /**
     * Returns the number of team members of a given project's id.
     *
     * @return the number of projects.
     */
    long countTeamMembersByProjectId(Long projectId);

    /**
     * retrieves all the users works on a given project with its id
     * @param projectId must not be null
     * @return all the users for the given project
     * @throws IllegalArgumentException if the given {@code projectId} is null
     */
    List<User> findAllByProjectId(Long projectId);
}
