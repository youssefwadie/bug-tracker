package com.github.youssefwadie.bugtracker.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.youssefwadie.bugtracker.dto.model.ProjectDto;
import com.github.youssefwadie.bugtracker.dto.mappers.ProjectMapper;
import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.project.ProjectService;
import com.github.youssefwadie.bugtracker.util.SimpleResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/projects")
public class AdminProjectController {
	private final ProjectMapper projectMapper;
	private final ProjectService projectService;

	@PostMapping("")
	public ResponseEntity<Object> createProject(@RequestBody ProjectDto projectDto) throws URISyntaxException {
		try {
			Project savedProject = projectService.save(projectMapper.dtoToProject(projectDto));
			URI resourceURI = new URI("http://localhost:8080/api/v1/projects/" + savedProject.getId());
			return ResponseEntity.created(resourceURI).build();
		} catch (IllegalArgumentException ex) {
			SimpleResponseBody responseBody = SimpleResponseBody.builder(HttpStatus.BAD_REQUEST)
					.message(ex.getMessage()).build();
			return ResponseEntity.badRequest().body(responseBody);
		}
	}

	@PutMapping(value = "/{id:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> assignTeamMembersToProject(@PathVariable("id") Long id,
			@RequestBody List<Long> developerIds) {
		try {
			projectService.addToProjectTeamMembers(id, developerIds);
			return ResponseEntity.ok(projectMapper.modelToDto(projectService.findById(id).get()));
		} catch (IllegalArgumentException ex) {
			SimpleResponseBody responseBody = SimpleResponseBody.builder(HttpStatus.BAD_REQUEST)
					.message(ex.getMessage()).build();
			return ResponseEntity.badRequest().body(responseBody);
		}
	}

}
