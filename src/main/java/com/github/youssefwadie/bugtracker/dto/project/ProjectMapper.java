package com.github.youssefwadie.bugtracker.dto.project;

import com.github.youssefwadie.bugtracker.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(imports = com.github.youssefwadie.bugtracker.model.User.class)
@Component
public interface ProjectMapper {

    @Mapping(target = "teamMembers", expression = "java(project.getTeamMembers().stream().map(User::getId).toList())")
    ProjectDto modelToDto(Project project);


    @Mapping(target = "teamMembers", expression = "java(projectDto.getTeamMembers().stream().map(User::new).toList())")
    Project dtoToProject(ProjectDto projectDto);

    List<ProjectDto> modelsToDtos(List<Project> projects);
}
