package com.github.youssefwadie.bugtracker.admin;

import com.github.youssefwadie.bugtracker.dto.project.ProjectDto;
import com.github.youssefwadie.bugtracker.dto.project.ProjectMapper;
import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.project.ProjectService;
import com.github.youssefwadie.bugtracker.util.SimpleResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/projects")
public class AdminProjectController {
    private final ProjectMapper projectMapper;
    private final ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<Object> createProject(@RequestBody ProjectDto projectDto) throws URISyntaxException {
        try {
            Project savedProject = projectService.addProject(projectMapper.dtoToProject(projectDto));
            URI resourceURI = new URI("http://localhost:8080/api/v1/projects/" + savedProject.getId());
            return ResponseEntity.created(resourceURI).build();
        } catch (IllegalArgumentException ex) {
            SimpleResponseBody responseBody =
                    SimpleResponseBody.builder(HttpStatus.BAD_REQUEST).message(ex.getMessage()).build();
            return ResponseEntity.badRequest().body(responseBody);
        }
    }


//    @PostMapping("{id:\\d+}")
//    public ResponseEntity<ProjectDto> assignTeamMembersToProject(@PathVariable("id") Long id, @RequestBody List<Long> developerIds) {
//        projectService.addMemberToProject(id, developerIds);
//    }

}
