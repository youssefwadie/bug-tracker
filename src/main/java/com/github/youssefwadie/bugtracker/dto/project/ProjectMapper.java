package com.github.youssefwadie.bugtracker.dto.project;

import com.github.youssefwadie.bugtracker.model.Project;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(imports = com.github.youssefwadie.bugtracker.dto.user.UserDto.class,
        uses = {
                com.github.youssefwadie.bugtracker.dto.user.UserMapper.class,
                com.github.youssefwadie.bugtracker.dto.ticket.TicketMapper.class
        }
)
@Component
public interface ProjectMapper {

    ProjectDto modelToDto(Project project);


    Project dtoToProject(ProjectDto projectDto);

    List<ProjectDto> modelsToDtos(List<Project> projects);
}
