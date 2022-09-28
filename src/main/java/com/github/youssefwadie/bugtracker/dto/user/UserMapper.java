package com.github.youssefwadie.bugtracker.dto.user;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.github.youssefwadie.bugtracker.model.User;

@Mapper
@Component
public interface UserMapper {
    @Mapping(target = "fullName", source = "fullName")
    UserDto modelToDto(User user);

    @Mapping(target = "fullName", source = "fullName")
    User dtoToModel(UserDto userDto);


    List<UserDto> modelsToDtos(List<User> users);

}
