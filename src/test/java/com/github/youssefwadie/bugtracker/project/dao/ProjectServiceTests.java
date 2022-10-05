package com.github.youssefwadie.bugtracker.project.dao;


import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.project.ProjectService;
import com.github.youssefwadie.bugtracker.project.ProjectValidatorService;
import com.github.youssefwadie.bugtracker.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProjectServiceTests {

    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    ProjectRepository projectRepository;
    @MockBean
    ProjectValidatorService validatorService;
    @MockBean
    UserService userService;

    @Autowired
    ProjectService projectService;


    @Test
    void saveNewProjectWithTeamMembersTest() {
        final Set<User> teamMembers = Set.of(new User(1L), new User(2L), new User(3L));
        final Project project = new Project();
        final String projectName = "Project X";
        final String projectDescription = "A test project";
        project.setName(projectName);
        project.setDescription(projectDescription);
        project.setTeamMembers(teamMembers);

        Project expectedSavedProject = new Project();
        expectedSavedProject.setId(50L);
        expectedSavedProject.setName(projectName);
        expectedSavedProject.setDescription(projectDescription);
        expectedSavedProject.setTeamMembers(teamMembers);

        Mockito.when(userService.existsAllByIds(expectedSavedProject.getTeamMembers()
                .stream().map(User::getId).sorted().toList())).thenReturn(true);
        Mockito.when(projectRepository.save(project)).thenReturn(expectedSavedProject);

        Project savedProject = projectService.save(project);

        Mockito.verify(projectRepository, Mockito.times(1))
                .addUserToProjectTeamMembers(1L, expectedSavedProject.getId());
        Mockito.verify(projectRepository, Mockito.times(1))
                .addUserToProjectTeamMembers(2L, expectedSavedProject.getId());
        Mockito.verify(projectRepository, Mockito.times(1))
                .addUserToProjectTeamMembers(3L, expectedSavedProject.getId());

    }


    @Test
    void saveNewProjectWithNoTeamMembers() {
        final String projectName = "Project X";
        final String projectDescription = "A test project";
        final Project project = new Project();

        project.setName(projectName);
        project.setDescription(projectDescription);
        project.setTeamMembers(null);

        Project expectedSavedProject = new Project();
        expectedSavedProject.setId(50L);
        expectedSavedProject.setName(projectName);
        expectedSavedProject.setDescription(projectDescription);
        expectedSavedProject.setTeamMembers(null);

        Mockito.when(projectRepository.save(project)).thenReturn(expectedSavedProject);

        Project savedProject = projectService.save(project);

        Mockito.verify(projectRepository, Mockito.never()).addUserToProjectTeamMembers(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(projectRepository, Mockito.never()).removeUserFromProjectTeamMembers(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void updateProjectWithNoTeamMembers() {
        final long projectId = 5L;
        final String projectName = "Project X";
        final String projectDescription = "A test project";
        final Project project = new Project();
        project.setId(projectId);
        project.setName(projectName);
        project.setDescription(projectDescription);
        project.setTeamMembers(null);


        final Set<User> oldTeamMembers = Set.of(new User(1L), new User(2L), new User(3L));
        Project projectFromTheDatabase = new Project();
        projectFromTheDatabase.setId(projectId);
        projectFromTheDatabase.setName(projectName);
        projectFromTheDatabase.setDescription(projectDescription);
        projectFromTheDatabase.setTeamMembers(oldTeamMembers);

        Mockito.when(projectRepository.getTeamMemberIds(projectId)).thenReturn(List.of(1L, 2L, 3L));
        Mockito.when(projectRepository.save(project)).thenReturn(project);

        Project savedProject = projectService.save(project);

        Mockito.verify(projectRepository, Mockito.times(1)).removeUserFromProjectTeamMembers(1L, projectId);
        Mockito.verify(projectRepository, Mockito.times(1)).removeUserFromProjectTeamMembers(2L, projectId);
        Mockito.verify(projectRepository, Mockito.times(1)).removeUserFromProjectTeamMembers(3L, projectId);

        Mockito.verify(projectRepository, Mockito.times(oldTeamMembers.size())).removeUserFromProjectTeamMembers(Mockito.anyLong(), Mockito.anyLong());
    }


    @Test
    void updateProjectWithModifiedTeamMembers() {
        final long projectId = 5L;
        final String projectName = "Project X";
        final String projectDescription = "A test project";
        final Set<User> newTeamMembers = Set.of(new User(1L), new User(2L), new User(5L), new User(6L));

        final Project editedProject = new Project();
        editedProject.setId(projectId);
        editedProject.setName(projectName);
        editedProject.setDescription(projectDescription);
        editedProject.setTeamMembers(newTeamMembers);

        final Set<User> oldTeamMembers = Set.of(new User(1L), new User(2L), new User(3L));
        Project expectedSavedProject = new Project();
        expectedSavedProject.setId(projectId);
        expectedSavedProject.setName(projectName);
        expectedSavedProject.setDescription(projectDescription);
        expectedSavedProject.setTeamMembers(oldTeamMembers);

        Mockito.when(projectRepository.getTeamMemberIds(projectId))
                .thenReturn(oldTeamMembers.stream().map(User::getId).sorted().collect(Collectors.toList()));
        Mockito.when(userService.existsAllByIds(newTeamMembers.stream().map(User::getId).sorted().toList())).thenReturn(true);
        Mockito.when(projectRepository.save(editedProject)).thenReturn(editedProject);

        Project savedProject = projectService.save(editedProject);

        Mockito.verify(projectRepository, Mockito.times(1))
                .addUserToProjectTeamMembers(5L, expectedSavedProject.getId());

        Mockito.verify(projectRepository, Mockito.times(1))
                .addUserToProjectTeamMembers(6L, expectedSavedProject.getId());

        Mockito.verify(projectRepository, Mockito.times(1))
                .removeUserFromProjectTeamMembers(3L, expectedSavedProject.getId());

        Mockito.verify(projectRepository, Mockito.times(2)).addUserToProjectTeamMembers(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(projectRepository, Mockito.times(1)).removeUserFromProjectTeamMembers(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void retainTeamMembersTest() {
        final long projectId = 2L;
        final List<Long> oldTeamMemberIds = List.of(16L, 21L, 23L, 24L, 28L, 30L, 32L, 35L, 36L, 43L, 45L);
        Mockito.when(projectRepository.getTeamMemberIds(projectId)).thenReturn(oldTeamMemberIds);
        final List<Long> newTeamMemberIds = List.of(1L, 16L, 21L, 23L, 24L, 28L, 30L, 32L, 80L, 900L);

        projectService.retainTeamMembers(projectId, newTeamMemberIds);

        Mockito.verify(projectRepository, Mockito.times(1)).removeUserFromProjectTeamMembers(36L, projectId);
        Mockito.verify(projectRepository, Mockito.times(1)).removeUserFromProjectTeamMembers(43L, projectId);
        Mockito.verify(projectRepository, Mockito.times(1)).removeUserFromProjectTeamMembers(45L, projectId);

        Mockito.verify(projectRepository, Mockito.times(1)).addUserToProjectTeamMembers(1L, projectId);
        Mockito.verify(projectRepository, Mockito.times(1)).addUserToProjectTeamMembers(80L, projectId);
        Mockito.verify(projectRepository, Mockito.times(1)).addUserToProjectTeamMembers(900L, projectId);

        Mockito.verify(projectRepository, Mockito.times(3)).addUserToProjectTeamMembers(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(projectRepository, Mockito.times(4)).removeUserFromProjectTeamMembers(Mockito.anyLong(), Mockito.anyLong());
    }

}
