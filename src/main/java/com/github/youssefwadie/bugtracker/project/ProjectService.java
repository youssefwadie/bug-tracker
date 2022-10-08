package com.github.youssefwadie.bugtracker.project;

import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.project.dao.ProjectRepository;
import com.github.youssefwadie.bugtracker.user.services.UserService;
import com.github.youssefwadie.bugtracker.util.ListDiff;
import com.github.youssefwadie.bugtracker.util.ListUtils;
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

    private final ProjectRepository projectRepository;
    private final ProjectValidatorService validatorService;
    private final UserService userService;

    public Optional<Project> findById(Long id) {
        return projectRepository.findByIdFetchTeamMembers(id, Pageable.unpaged());
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public void addToProjectTeamMembers(Long projectId, List<Long> userIds) {
        if (!this.existsById(projectId)) {
            throw new ProjectNotFoundException(String.format(PROJECT_NOT_FOUND_MSG, projectId));
        }
        addUsersToProjectTeamMembers(projectId, userIds);
    }

    public boolean existsById(Long projectId) {
        return projectId != null && projectRepository.existsById(projectId);
    }

    public void addUsersToProjectTeamMembers(Long projectId, List<Long> userIds) {
        boolean allTeamMembersExists = userService.existsAllByIds(userIds);
        if (!allTeamMembersExists) {
            throw new IllegalArgumentException("some or all users are unknown");
        }

        for (Long userId : userIds) {
            if (!projectRepository.userWorksOnProject(userId, projectId)) {
                projectRepository.addUserToProjectTeamMembers(userId, projectId);
            }
        }

    }

    @Transactional
    public Project save(Project project) {
        validatorService.validateProject(project);
        final Long projectId = project.getId();

        if (project.getTeamMembers() == null || project.getTeamMembers().isEmpty()) {
            if (projectId != null) {
                final List<Long> teamMemberIds = projectRepository.getTeamMemberIds(projectId);
                teamMemberIds.forEach(teamMemberId -> projectRepository.removeUserFromProjectTeamMembers(teamMemberId, projectId));
            }
            return projectRepository.save(project);
        }

        final List<Long> teamMemberIds = project.getTeamMembers().stream().map(User::getId).sorted().toList();
        boolean allTeamMembersExists = userService.existsAllByIds(teamMemberIds);
        if (!allTeamMembersExists) {
            throw new IllegalArgumentException("some or all users are unknown");
        }

        if (projectId == null) {
            Project savedProject = projectRepository.save(project);
            final long savedProjectId = savedProject.getId();
            teamMemberIds.forEach(teamMemberId -> projectRepository.addUserToProjectTeamMembers(teamMemberId, savedProjectId));
            return savedProject;
        }

        Project savedProject = projectRepository.save(project);
        this.retainTeamMembers(savedProject.getId(), teamMemberIds);

        return savedProject;
    }


    /**
     * Removes all team members of a given project except the ones in the {@code teamMembersIds} list.
     *
     * @param projectId      must not be {@literal null}.
     * @param teamMembersIds must not be {@literal null} and sorted in ascending order.
     */
    public void retainTeamMembers(Long projectId, List<Long> teamMembersIds) {
        List<Long> teamMembersIdsInDb = projectRepository.getTeamMemberIds(projectId);

        final ListDiff<Long> diffLists = ListUtils.diffLists(teamMembersIdsInDb, teamMembersIds);
        final List<Long> teamMembersToDelete = diffLists.getElementsInFirstListOnly();
        for (final Long teamMemberId : teamMembersToDelete) {
            projectRepository.removeUserFromProjectTeamMembers(teamMemberId, projectId);
        }

        final List<Long> teamMembersToAdd = diffLists.getElementsInSecondListOnly();
        for (final Long newTeamMemberId : teamMembersToAdd) {
            projectRepository.addUserToProjectTeamMembers(newTeamMemberId, projectId);
        }
    }

    public Page<Project> findAllByPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PROJECTS_PER_PAGE);
        return projectRepository.findAll(pageable);
    }

    public long count() {
        return projectRepository.count();
    }

    public long countTeamMembersByProjectId(long projectId) {
        return userService.countTeamMembersByProjectId(projectId);
    }
}
