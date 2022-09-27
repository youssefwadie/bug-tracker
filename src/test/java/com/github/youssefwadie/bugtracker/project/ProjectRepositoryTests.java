package com.github.youssefwadie.bugtracker.project;


import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectRepositoryTests {
    @Autowired
    ProjectRepository projectRepository;

    @Test
    void findByIdWithTeamMembers() {
        Long projectId = 1L;
        Optional<Project> projectWithTeamMembersOptional
                = projectRepository.findByIdFetchTeamMembers(projectId);
        assertThat(projectWithTeamMembersOptional.isPresent()).isTrue();
        Project projectWithTeamMembers = projectWithTeamMembersOptional.get();
        assertThat(projectWithTeamMembers.getTeamMembers().size()).isGreaterThan(0);
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
                = projectRepository.findByIdFetchTeamMembers(savedProject.getId());
        assertThat(projectWithTeamMembersOptional.isPresent()).isTrue();
        Project projectWithTeamMembers = projectWithTeamMembersOptional.get();

        assertThat(projectWithTeamMembers.getTeamMembers().size()).isEqualTo(teamMembers.size());
    }

    @Test
    void findByIdFetchAllTest() {
        Long id = 1L;
        Optional<Project> projectOptional = projectRepository.findByIdFetchAll(id);
        assertThat(projectOptional.isPresent()).isTrue();
        Project project = projectOptional.get();
        assertThat(project.getTeamMembers().size()).isGreaterThan(0);
        assertThat(project.getTickets().size()).isGreaterThan(0);
    }
}
