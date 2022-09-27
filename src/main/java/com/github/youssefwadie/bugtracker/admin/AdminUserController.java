package com.github.youssefwadie.bugtracker.admin;

import com.github.youssefwadie.bugtracker.dto.user.UserDto;
import com.github.youssefwadie.bugtracker.dto.user.UserMapper;
import com.github.youssefwadie.bugtracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("")
    public ResponseEntity<List<UserDto>> listUsers() {
        return ResponseEntity.ok(userMapper.modelsToDtos(userService.findAll()));
    }
}
