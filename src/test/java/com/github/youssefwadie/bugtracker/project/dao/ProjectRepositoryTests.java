package com.github.youssefwadie.bugtracker.project.dao;


import com.github.youssefwadie.bugtracker.AbstractJdbcTest;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.project.dao.ProjectRepository;
import com.github.youssefwadie.bugtracker.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProjectRepositoryTests extends AbstractJdbcTest {
    @Autowired
    ProjectRepository projectRepository;

    @Test
    void testFindAllWithSort() {
        final Sort.Order[] orders = new Sort.Order[]{
                Sort.Order.asc("name"),
                Sort.Order.desc("description"),
        };

        final Comparator<Project> comparator = Comparator.comparing(Project::getName)
                .thenComparing(Comparator.comparing(Project::getDescription).reversed());

        final Sort sort = Sort.by(orders);
        Iterable<Project> all = projectRepository.findAll(sort);
        boolean sorted = isSorted(all, comparator);
        assertThat(sorted).isTrue();
    }

    @Test
    void testFindAllWithPageAndSort() {
        final Sort.Order[] orders = new Sort.Order[]{
                Sort.Order.asc("name"),
                Sort.Order.desc("description"),
        };

        final Comparator<Project> comparator = Comparator.comparing(Project::getName)
                .thenComparing(Comparator.comparing(Project::getDescription).reversed());

        final Sort sort = Sort.by(orders);

        final int pageSize = 10;
        final int pageNumber = 0;

        final Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Project> page = projectRepository.findAll(pageable);
        boolean sorted = super.isSorted(page.getContent(), comparator);
        assertThat(page.getContent().size()).isEqualTo(pageSize);
        assertThat(sorted).isTrue();
    }

    @Test
    void findByIdWithTeamMembers() {
        final long projectId = 2L;
        final int pageNumber = 0;
        final int pageSize = 5;

        final Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Optional<Project> projectWithTeamMembersOptional = projectRepository.findByIdFetchTeamMembers(projectId, pageable);
        assertThat(projectWithTeamMembersOptional.isPresent()).isTrue();

        Project projectWithTeamMembers = projectWithTeamMembersOptional.get();
        assertThat(projectWithTeamMembers.getTeamMembers().size()).isGreaterThan(0);
        assertThat(projectWithTeamMembers.getTeamMembers().size()).isLessThanOrEqualTo(pageSize);
        assertThat(projectWithTeamMembers.getTickets().size()).isEqualTo(0);
    }

    @Test
    void saveProjectTest() {
        Project project = new Project();
        project.setName("Project title");
        project.setDescription("Project description");


        Set<User> teamMembers = Set.of(new User(1L));
        project.setTeamMembers(teamMembers);

        Project savedProject = projectRepository.save(project);
        Optional<Project> projectWithTeamMembersOptional
                = projectRepository.findByIdFetchTeamMembers(savedProject.getId(), Pageable.unpaged());
        assertThat(projectWithTeamMembersOptional.isPresent()).isTrue();
        Project projectWithTeamMembers = projectWithTeamMembersOptional.get();

        assertThat(projectWithTeamMembers.getTeamMembers().size()).isEqualTo(teamMembers.size());
    }

    @Test
    void doesUserWorkOnProjectTest() {
        Long userId = 1L;
        Long projectId = 8L;
        boolean doesUserWorksOnProject = projectRepository.doesUserWorkOnProject(userId, projectId);
        assertThat(doesUserWorksOnProject).isTrue();
    }
}
