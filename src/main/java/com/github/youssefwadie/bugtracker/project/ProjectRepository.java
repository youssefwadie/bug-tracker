package com.github.youssefwadie.bugtracker.project;

import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * TODO: Hibernate fetches all the collections so, a transition to JDBC based repositories will be made
 */

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT new com.github.youssefwadie.bugtracker.model.Project(p.id, p.name, p.description) FROM Project p")
    List<Project> findAll();

    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.teamMembers WHERE p.id = ?1")
    Optional<Project> findByIdFetchTeamMembers(Long id);

    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.teamMembers LEFT JOIN FETCH p.tickets WHERE p.id = ?1")
    Optional<Project> findByIdFetchAll(Long id);

    @Query("SELECT new com.github.youssefwadie.bugtracker.model.Project(p.id, p.name, p.description) FROM Project p")
    Page<Project> findAll(Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM works_on WHERE project_id = ?1", nativeQuery = true)
    Long countTeamMembersByProjectId(Long projectId);

    @Query(value = "SELECT u.* FROM users u JOIN works_on wo ON u.id = wo.user_id WHERE wo.project_id = ?1", nativeQuery = true)
    Page<User> findAllTeamMembers(Long projectId, Pageable pageable);
}
