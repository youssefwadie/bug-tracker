package com.github.youssefwadie.bugtracker.jdbc.project;

import com.github.youssefwadie.bugtracker.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface ProjectRepositoryJdbc {

    /**
     * Saves a given project. Use the returned instance for further operations as the save operation might have changed the
     * project instance completely.
     *
     * @param project must not be {@literal null}.
     * @return the saved project; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    Project save(Project project);

    /**
     * Saves all given projects.
     *
     * @param projects must not be {@literal null} nor must it contain {@literal null}.
     * @return the saved entities; will never be {@literal null}. The returned {@literal Iterable} will have the same size
     * as the {@literal Iterable} passed as an argument.
     * @throws IllegalArgumentException in case the given {@link Iterable projects} or one of its projects is
     *                                  {@literal null}.
     */
    Iterable<Project> saveAll(Iterable<Project> projects);

    /**
     * Retrieves a project by its id.
     *
     * @param id must not be {@literal null}.
     * @return the {@link Project} with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    Optional<Project> findById(Long id);


    /**
     * Returns whether a project with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if an project with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    boolean existsById(Long id);

    /**
     * Returns all instances of the {@link Project}.
     *
     * @return all projects
     */
    Iterable<Project> findAll();

    /**
     * Returns all instances of the type {@link  Project} with the given IDs.
     * <p>
     * If some or all ids are not found, no entities are returned for these IDs.
     * <p>
     * Note that the order of elements in the result is not guaranteed.
     *
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
     * {@literal ids}.
     * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its elements is {@literal null}..
     */
    Iterable<Project> findAllById(Iterable<Long> ids);

    /**
     * Returns the number of projects available.
     *
     * @return the number of projects.
     */
    long count();

    /**
     * Deletes the project with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    void deleteById(Long id);

    /**
     * Deletes a given project.
     *
     * @param project must not be {@literal null}.
     * @throws IllegalArgumentException in case the given project is {@literal null} or {@code project.id} is null.
     */
    void delete(Project project);

    /**
     * Deletes all instances of the type {@link Project} with the given IDs.
     *
     * @param ids must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal ids} or one of its elements is {@literal null}.
     */
    void deleteAllById(Iterable<? extends Long> ids);

    /**
     * Deletes the given projects.
     *
     * @param projects must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal projects} or one of its elements is {@literal null}.
     */
    void deleteAll(Iterable<Project> projects);

    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();

    /**
     * Returns all projects sorted by the given options.
     *
     * @param sort the {@link Sort} specification to sort the results by, can be {@link Sort#unsorted()}, must not be
     *             {@literal null}.
     * @return all projects sorted by the given options
     */
    Iterable<Project> findAll(Sort sort);

    /**
     * Returns a {@link Page} of projects meeting the paging restriction provided in the {@link Pageable} object.
     *
     * @param pageable the pageable to request a paged result, can be {@link Pageable#unpaged()}, must not be
     *                 {@literal null}.
     * @return a page of projects
     */
    Page<Project> findAll(Pageable pageable);

    /**
     * Retrieves a project with its team members by its id.
     *
     * @param id must not be {@literal null}.
     * @return the {@link Project} with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    Optional<Project> findByIdFetchTeamMembers(Long id);

    /**
     * Returns the number of team members of a given project's id.
     *
     * @return the number of projects.
     */
    long countTeamMembersByProjectId(Long projectId);
}
