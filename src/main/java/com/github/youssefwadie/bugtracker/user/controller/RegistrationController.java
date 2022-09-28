package com.github.youssefwadie.bugtracker.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.youssefwadie.bugtracker.model.RegistrationRequest;
import com.github.youssefwadie.bugtracker.user.service.RegistrationService;
import com.github.youssefwadie.bugtracker.util.SimpleResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerNewUser(@RequestBody RegistrationRequest registrationRequest) {
        String message = registrationService.register(registrationRequest);
        final SimpleResponseBody responseBody = SimpleResponseBody
                .builder(HttpStatus.OK)
                .message("checkout your mail box").build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);

    }


    @GetMapping("confirm")
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token) {
        try {
            String message = registrationService.confirmToken(token);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException ex) {
            SimpleResponseBody responseBody = SimpleResponseBody.builder(HttpStatus.BAD_REQUEST).message(ex.getMessage()).build();
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
