package com.github.youssefwadie.bugtracker.dto.mappers;


import com.github.youssefwadie.bugtracker.dto.model.UserDto;
import com.github.youssefwadie.bugtracker.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    @Mapping(target = "fullName", source = "fullName")
    UserDto userToUserDto(User user);

    @Mapping(target = "fullName", source = "fullName")
    User userDtoToUser(UserDto userDto);


    List<UserDto> usersToUsersDto(List<User> users);

}
