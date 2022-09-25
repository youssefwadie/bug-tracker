package com.github.youssefwadie.bugtracker.project;

import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectValidatorService validatorService;
    private final UserService userService;

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public void addMemberToProject(Long projectId, List<Long> developerIds) {

    }

    @Transactional
    public Project addProject(Project project) {
        validatorService.validateProject(project);
        List<Long> teamMembersIds = project.getTeamMembers().stream().map(User::getId).toList();
        boolean allTeamMembersExists = userService.existsAllByIds(teamMembersIds);
        if (!allTeamMembersExists) {
            throw new IllegalArgumentException("some or all users are unknown");
        }

        return projectRepository.save(project);
    }


}
