package com.github.youssefwadie.bugtracker.dto.mappers;

import com.github.youssefwadie.bugtracker.dto.model.TicketCommentDto;
import com.github.youssefwadie.bugtracker.dto.model.UserDto;
import com.github.youssefwadie.bugtracker.model.TicketComment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(imports = UserDto.class,
        uses = {
                UserMapper.class
        })

public interface TicketCommentMapper {

    @Mapping(target = "commenter", source = "commenter")
    TicketCommentDto commentToCommentDto(TicketComment comment);

    @InheritInverseConfiguration
    TicketComment commentDtoToComment(TicketCommentDto commentDto);

    List<TicketCommentDto> commentsToCommentsDTOs(List<TicketComment> ticketComments);
}
