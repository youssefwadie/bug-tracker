package com.github.youssefwadie.bugtracker.project;

import com.github.youssefwadie.bugtracker.dto.mappers.ProjectMapper;
import com.github.youssefwadie.bugtracker.dto.mappers.TicketMapper;
import com.github.youssefwadie.bugtracker.dto.mappers.UserMapper;
import com.github.youssefwadie.bugtracker.dto.model.ProjectDto;
import com.github.youssefwadie.bugtracker.dto.model.TicketDto;
import com.github.youssefwadie.bugtracker.dto.model.UserDto;
import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.model.Ticket;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.ticket.TicketService;
import com.github.youssefwadie.bugtracker.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.github.youssefwadie.bugtracker.constants.ResponseConstants.TOTAL_COUNT_HEADER_NAME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final TicketService ticketService;
    private final UserService userService;

    private final ProjectMapper projectMapper;
    private final TicketMapper ticketMapper;
    private final UserMapper userMapper;


    @GetMapping("/page/{pageNumber:[1-9]\\d*}")
    public ResponseEntity<List<ProjectDto>> listByPage(@PathVariable("pageNumber") Integer pageNumber) {
        Page<Project> projectsPage = projectService.findAllByPage(pageNumber - 1);

        return ResponseEntity.ok()
                .header(TOTAL_COUNT_HEADER_NAME, Long.toString(projectsPage.getTotalElements()))
                .body(projectMapper.projectsToProjectsDto(projectsPage.getContent()));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(projectService.count());
    }

    @GetMapping("/{id:[1-9]\\d*}")
    public ResponseEntity<ProjectDto> getById(@PathVariable Long id) {
        Optional<Project> projectById = projectService.findById(id);
        if (projectById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectMapper.projectToProjectDto(projectById.get()));
    }

    @GetMapping("/{id:[1-9]\\d*}/members/count")
    public ResponseEntity<Long> getTeamMembersCount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projectService.countTeamMembersByProjectId(id));
    }

    @GetMapping("/{projectId:[1-9]\\d*}/members/page/{pageNumber:[1-9]\\d*}")
    public ResponseEntity<List<UserDto>> listTeamMembersByPage(@PathVariable("projectId") Long projectId,
                                                               @PathVariable("pageNumber") Integer pageNumber) {
        final Page<User> usersPage = userService.findAllByProject(projectId, pageNumber - 1);
        return ResponseEntity.ok()
                .header(TOTAL_COUNT_HEADER_NAME, Long.toString(usersPage.getTotalElements()))
                .body(userMapper.usersToUsersDto(usersPage.getContent()));
    }

    @GetMapping("/{projectId:[1-9]\\d*}/tickets/page/{pageNumber:[1-9]\\d*}")
    public ResponseEntity<List<TicketDto>> listTicketsByPage(@PathVariable("projectId") Long projectId,
                                                             @PathVariable("pageNumber") Integer pageNumber) {
        Page<Ticket> ticketsPage = ticketService.findAllTicketsByProject(projectId, pageNumber - 1);
        return ResponseEntity.ok()
                .header(TOTAL_COUNT_HEADER_NAME, Long.toString(ticketsPage.getTotalElements()))
                .body(ticketMapper.ticketsToTicketsDto(ticketsPage.getContent()));
    }
}
