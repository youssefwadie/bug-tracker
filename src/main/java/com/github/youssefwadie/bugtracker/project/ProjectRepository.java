package com.github.youssefwadie.bugtracker.project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.youssefwadie.bugtracker.model.Project;

/**
 * TODO: Hibernate fetches all the collections so, a transition to JDBC based repositories will be done
*/

public interface ProjectRepository extends JpaRepository<Project, Long> {

	@Query("SELECT new com.github.youssefwadie.bugtracker.model.Project(p.id, p.name, p.description) FROM Project p")
	List<Project> findAll();

	@Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.teamMembers WHERE p.id = ?1")
	Optional<Project> findByIdFetchTeamMembers(Long id);

	@Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.teamMembers LEFT JOIN FETCH p.tickets WHERE p.id = ?1")
	Optional<Project> findByIdFetchAll(Long id);
}
