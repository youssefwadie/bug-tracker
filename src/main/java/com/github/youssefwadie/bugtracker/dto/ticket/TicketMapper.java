package com.github.youssefwadie.bugtracker.dto.ticket;

import com.github.youssefwadie.bugtracker.model.Ticket;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TicketMapper {

    @Mapping(source = "assignedDeveloperId", target = "assignedDeveloper")
    @Mapping(source = "projectId", target = "project")
//    @Mapping(source = "type.description", target = "type")
//    @Mapping(source = "priority.description", target = "priority")
//    @Mapping(source = "status.description", target = "status")
    TicketDto modelToDto(Ticket ticket);

    @InheritInverseConfiguration
    Ticket dtoToModel(TicketDto ticketDto);
}