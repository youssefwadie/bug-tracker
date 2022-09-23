package com.github.youssefwadie.bugtracker.user.controllers;

import com.github.youssefwadie.bugtracker.dto.user.UserDto;
import com.github.youssefwadie.bugtracker.dto.user.UserMapper;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.service.BugTrackerUserDetails;
import com.github.youssefwadie.bugtracker.user.UserService;
import com.github.youssefwadie.bugtracker.user.service.RegistrationService;
import com.github.youssefwadie.bugtracker.util.SimpleResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserAuthController {
    private final UserMapper userMapper;
    private final RegistrationService registrationService;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<UserDto> login() {
        UserDto userDto = userMapper.modelToDto(getLoggedInUser());
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }


    @GetMapping(value = "resend", produces = "application/json")
    public ResponseEntity<?> resendConfirmationToken(@RequestParam("email") String email) {
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isEmpty()) {
            SimpleResponseBody responseBody = SimpleResponseBody
                    .builder(HttpStatus.BAD_REQUEST)
                    .setMessage("Invalid email")
                    .build();
            return ResponseEntity.badRequest().body(responseBody);
        }

        final User loggedInUser = userByEmail.get();

        String confirmationToken = registrationService.resendConfirmationToken(loggedInUser);
        final SimpleResponseBody responseBody = SimpleResponseBody
                .builder(HttpStatus.OK)
                .setMessage("checkout your mail box").build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    private User getLoggedInUser() {
        BugTrackerUserDetails loggedInPrincipal = (BugTrackerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggedInPrincipal.getUser();
    }
}
