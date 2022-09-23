package com.github.youssefwadie.bugtracker.dto.user;


import com.github.youssefwadie.bugtracker.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    @Mapping(target = "fullName", source = "fullName")
    UserDto modelToDto(User user);

    @Mapping(target = "fullName", source = "fullName")
    User dtoToModel(UserDto userDto);


    List<UserDto> modelsToDtos(List<User> users);

}
