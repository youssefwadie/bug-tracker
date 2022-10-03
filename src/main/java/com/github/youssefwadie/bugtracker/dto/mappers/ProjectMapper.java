package com.github.youssefwadie.bugtracker.dto.mappers;

import com.github.youssefwadie.bugtracker.dto.model.ProjectDto;
import com.github.youssefwadie.bugtracker.dto.model.UserDto;
import com.github.youssefwadie.bugtracker.model.Project;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(imports = UserDto.class,
        uses = {
                UserMapper.class,
                TicketMapper.class
        }
)
@Component
public interface ProjectMapper {

    ProjectDto projectToProjectDto(Project project);


    Project projectDtoToProject(ProjectDto projectDto);

    List<ProjectDto> projectsToProjectsDto(List<Project> projects);
}
