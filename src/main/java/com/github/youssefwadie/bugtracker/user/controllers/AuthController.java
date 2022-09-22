package com.github.youssefwadie.bugtracker.user.controllers;

import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.BugTrackerUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class AuthController {
    @PostMapping("login")
    public ResponseEntity<User> login() {
        BugTrackerUserDetails loggedInPrincipal = (BugTrackerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(loggedInPrincipal.getUser());
    }
}
