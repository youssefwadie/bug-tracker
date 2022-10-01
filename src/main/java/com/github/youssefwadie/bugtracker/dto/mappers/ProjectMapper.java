package com.github.youssefwadie.bugtracker.dto.mappers;

import java.util.List;

import com.github.youssefwadie.bugtracker.dto.model.ProjectDto;
import com.github.youssefwadie.bugtracker.dto.model.UserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.github.youssefwadie.bugtracker.model.Project;

@Mapper(imports = UserDto.class,
        uses = {
                UserMapper.class,
                TicketMapper.class
        }
)
@Component
public interface ProjectMapper {

    ProjectDto modelToDto(Project project);


    Project dtoToProject(ProjectDto projectDto);

    List<ProjectDto> modelsToDtos(List<Project> projects);
}
