package com.github.youssefwadie.bugtracker.dto.mappers;

import com.github.youssefwadie.bugtracker.dto.model.TicketDto;
import com.github.youssefwadie.bugtracker.model.Ticket;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TicketMapper {

    @Mapping(source = "assignedDeveloperId", target = "assignedDeveloper")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "submitterId", target = "submitter")
//    @Mapping(source = "type.description", target = "type")
//    @Mapping(source = "priority.description", target = "priority")
//    @Mapping(source = "status.description", target = "status")
    TicketDto modelToDto(Ticket ticket);

    @InheritInverseConfiguration
    Ticket dtoToModel(TicketDto ticketDto);

    List<TicketDto> modelsToDtos(List<Ticket> tickets);
}
