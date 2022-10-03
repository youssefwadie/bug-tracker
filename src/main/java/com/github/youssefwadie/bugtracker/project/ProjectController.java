package com.github.youssefwadie.bugtracker.project;

import com.github.youssefwadie.bugtracker.dto.mappers.UserMapper;
import com.github.youssefwadie.bugtracker.dto.model.ProjectDto;
import com.github.youssefwadie.bugtracker.dto.mappers.ProjectMapper;
import com.github.youssefwadie.bugtracker.dto.model.UserDto;
import com.github.youssefwadie.bugtracker.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAll() {
        return ResponseEntity.ok(projectMapper.projectsToProjectsDto(projectService.findAll()));
    }

    @GetMapping("/page/{pageNumber:\\d+}")
    public ResponseEntity<List<ProjectDto>> listByPage(@PathVariable("pageNumber") Integer pageNumber) {
        return ResponseEntity.ok(projectMapper.projectsToProjectsDto(projectService.listByPage(pageNumber)));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(projectService.count());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<ProjectDto> findById(@PathVariable Long id) {
        Optional<Project> projectById = projectService.findById(id);
        if (projectById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectMapper.projectToProjectDto(projectById.get()));
    }

    @GetMapping("/{id:\\d+}/members/count")
    public ResponseEntity<Long> getTeamMembersCount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projectService.countTeamMembersByProjectId(id));
    }

    @GetMapping("/{projectId:\\d}/members/page/{pageNumber:\\d}")
    public ResponseEntity<List<UserDto>> listTeamMembersByPage(@PathVariable("projectId") Long projectId,
                                                               @PathVariable("pageNumber") Integer pageNumber) {
        return ResponseEntity.ok(userMapper.usersToUsersDto(projectService.listTeamMembersByPage(projectId, pageNumber)));
    }
}
