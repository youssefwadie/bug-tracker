package com.github.youssefwadie.bugtracker.admin;

import com.github.youssefwadie.bugtracker.dto.mappers.UserMapper;
import com.github.youssefwadie.bugtracker.dto.model.UserDto;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.user.exceptions.UserNotFoundException;
import com.github.youssefwadie.bugtracker.user.services.UserService;
import com.github.youssefwadie.bugtracker.util.SimpleResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.youssefwadie.bugtracker.constants.ResponseConstants.TOTAL_COUNT_HEADER_NAME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userMapper.userToUserDto(userService.findById(id)));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(SimpleResponseBody.builder(HttpStatus.NOT_FOUND).message(ex.getMessage()).build());
        }
    }

    @GetMapping("page/{pageNumber:\\d+}")
    public ResponseEntity<Object> listByPage(@PathVariable("pageNumber") Integer pageNumber) {
        if (pageNumber == 0) {
            return ResponseEntity
                    .badRequest()
                    .body(SimpleResponseBody
                            .builder(HttpStatus.BAD_REQUEST)
                            .message("pageNumber must be a positive integer").build());
        }
        final Page<User> usersPage = userService.listByPage(pageNumber - 1);
        return ResponseEntity.ok()
                .header(TOTAL_COUNT_HEADER_NAME, Long.toString(usersPage.getTotalElements()))
                .body(userMapper.usersToUsersDto(usersPage.getContent()));
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUser(@RequestBody UserDto user) {
        if (user.getId() == null) {
            return ResponseEntity.badRequest()
                    .body(SimpleResponseBody.builder(HttpStatus.BAD_REQUEST).message("id is not provided").build());
        }
        User savedUser = userService.save(userMapper.userDtoToUser(user));
        return ResponseEntity.ok(userMapper.userToUserDto(savedUser));
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> listUsers() {
        return ResponseEntity.ok(userMapper.usersToUsersDto(userService.findAll()));
    }
}
