package com.github.youssefwadie.bugtracker.project;

import com.github.youssefwadie.bugtracker.project.dao.ProjectRepository;
import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private static final String PROJECT_NOT_FOUND_MSG = "No project with id %d is found";
    private static final int PROJECTS_PER_PAGE = 5;
    private static final int USERS_PER_PAGE = 5;

    private final ProjectRepository projectRepository;
    private final ProjectValidatorService validatorService;
    private final UserService userService;

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public void addToProjectTeamMembers(Long projectId, List<Long> userIds) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException(String.format(PROJECT_NOT_FOUND_MSG, projectId));
        }
        addUsersToProjectTeamMembers(projectId, userIds);
    }

    public void addUsersToProjectTeamMembers(Long projectId, List<Long> userIds) {
        boolean allTeamMembersExists = userService.existsAllByIds(userIds);
        if (!allTeamMembersExists) {
            throw new IllegalArgumentException("some or all users are unknown");
        }

        for (Long userId : userIds) {
            if (!projectRepository.doesUserWorkOnProject(userId, projectId)) {
                projectRepository.addUserToProjectTeamMembers(userId, projectId);
            }
        }

    }

    @Transactional
    public Project save(Project project) {
        validatorService.validateProject(project);
        if (project.getTeamMembers() != null) {
            List<Long> teamMembersIds = project.getTeamMembers().stream().map(User::getId).toList();
            boolean allTeamMembersExists = userService.existsAllByIds(teamMembersIds);
            if (!allTeamMembersExists) {
                throw new IllegalArgumentException("some or all users are unknown");
            }
        }

        return projectRepository.save(project);
    }

    public List<Project> listByPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PROJECTS_PER_PAGE);
        Page<Project> projectsPage = projectRepository.findAll(pageable);
        return projectsPage.getContent();
    }

    public long count() {
        return projectRepository.count();
    }

    public Long countTeamMembersByProjectId(Long projectId) {
        return userService.countTeamMembersByProjectId(projectId);
    }

    public List<User> listTeamMembersByPage(Long projectId, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, USERS_PER_PAGE);
        Page<User> userPage = userService.findAllTeamMembers(projectId, pageable);
        return userPage.getContent();
    }
}
