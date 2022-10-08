package com.github.youssefwadie.bugtracker.admin;

import com.github.youssefwadie.bugtracker.dto.mappers.ProjectMapper;
import com.github.youssefwadie.bugtracker.dto.model.ProjectDto;
import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.project.ProjectService;
import com.github.youssefwadie.bugtracker.util.SimpleResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/projects")
public class AdminProjectController {
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<Object> createProject(@RequestBody ProjectDto projectDto) {
        try {
            Project savedProject = projectService.save(projectMapper.projectDtoToProject(projectDto));
            URI resourceURI = URI.create("http://localhost:8080/api/v1/projects/" + savedProject.getId());
            return ResponseEntity.created(resourceURI).build();
        } catch (IllegalArgumentException ex) {
            SimpleResponseBody responseBody = SimpleResponseBody.builder(HttpStatus.BAD_REQUEST)
                    .message(ex.getMessage()).build();
            return ResponseEntity.badRequest().body(responseBody);
        }
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateProject(@RequestBody ProjectDto projectDto) {
        try {
            if (!projectService.existsById(projectDto.getId())) {
                return ResponseEntity
                        .badRequest()
                        .body(SimpleResponseBody.builder(HttpStatus.BAD_REQUEST).message("invalid id").build());
            }

            Project savedProject = projectService.save(projectMapper.projectDtoToProject(projectDto));
            return ResponseEntity.ok(savedProject);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(SimpleResponseBody.builder(HttpStatus.BAD_REQUEST).message(ex.getMessage()).build());
        }
    }

    @PutMapping(value = "/{id:[1-9]\\d*}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> assignTeamMembersToProject(@PathVariable("id") Long id,
                                                             @RequestBody List<Long> developerIds) {
        try {
            projectService.addToProjectTeamMembers(id, developerIds);
            return ResponseEntity.ok(projectMapper.projectToProjectDto(projectService.findById(id).get()));
        } catch (IllegalArgumentException ex) {
            SimpleResponseBody responseBody = SimpleResponseBody.builder(HttpStatus.BAD_REQUEST)
                    .message(ex.getMessage()).build();
            return ResponseEntity.badRequest().body(responseBody);
        }
    }

}
