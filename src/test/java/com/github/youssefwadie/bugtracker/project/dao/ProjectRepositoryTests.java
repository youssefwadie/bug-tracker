package com.github.youssefwadie.bugtracker.project.dao;


import com.github.youssefwadie.bugtracker.AbstractJdbcTest;
import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
        teamMembers.forEach(teamMember -> projectRepository.addUserToProjectTeamMembers(teamMember.getId(), savedProject.getId()));

        Optional<Project> projectWithTeamMembersOptional
                = projectRepository.findByIdFetchTeamMembers(savedProject.getId(), Pageable.unpaged());
        assertThat(projectWithTeamMembersOptional.isPresent()).isTrue();
        Project projectWithTeamMembers = projectWithTeamMembersOptional.get();

        assertThat(projectWithTeamMembers.getTeamMembers().size()).isEqualTo(teamMembers.size());
    }

    @Test
    void addUserToProjectTeamMembersTest() {
        assertThatThrownBy(() -> {
            final long userId = 50L;
            final long projectId = 5500L;
            projectRepository.addUserToProjectTeamMembers(userId, projectId);
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("projectId doesn't exist");

        assertThatThrownBy(() -> {
            final long userId = 50000L;
            final long projectId = 1L;
            projectRepository.addUserToProjectTeamMembers(userId, projectId);
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("userId doesn't exist");

        final long userId = 1L;
        final long projectId = 40L;
        boolean worksBeforeAdding = projectRepository.userWorksOnProject(userId, projectId);
        assertThat(worksBeforeAdding).isFalse();

        projectRepository.addUserToProjectTeamMembers(userId, projectId);
        boolean worksAfterAdding = projectRepository.userWorksOnProject(userId, projectId);
        assertThat(worksAfterAdding).isTrue();
    }


    @Test
    void getTeamMembersIdsTests() {
        final long projectId = 1;
        final List<Long> teamMemberIds = projectRepository.getTeamMemberIds(projectId);
        assertThat(teamMemberIds.size()).isEqualTo(1);
    }

    @Test
    void removeUserFromProjectTeamMembersTest() {
        final long userId = 1L;
        final long projectId = 1L;
        boolean worksBeforeRemoving = projectRepository.userWorksOnProject(userId, projectId);
        assertThat(worksBeforeRemoving).isTrue();

        projectRepository.removeUserFromProjectTeamMembers(userId, projectId);
        boolean worksAfterRemoving = projectRepository.userWorksOnProject(userId, projectId);
        assertThat(worksAfterRemoving).isFalse();
    }


    @Test
    void doesUserWorkOnProjectTest() {
        Long userId = 1L;
        Long projectId = 8L;
        boolean doesUserWorksOnProject = projectRepository.userWorksOnProject(userId, projectId);
        assertThat(doesUserWorksOnProject).isFalse();
    }
}
