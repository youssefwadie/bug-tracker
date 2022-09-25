package com.github.youssefwadie.bugtracker.project;

import com.github.youssefwadie.bugtracker.dto.project.ProjectDto;
import com.github.youssefwadie.bugtracker.dto.project.ProjectMapper;
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

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAll() {
        return ResponseEntity.ok(projectMapper.modelsToDtos(projectService.findAll()));
    }


    @GetMapping("/{id:\\d+}")
    public ResponseEntity<ProjectDto> findById(@PathVariable Long id) {
        Optional<Project> projectById = projectService.findById(id);
        if (projectById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectMapper.modelToDto(projectById.get()));
    }

}