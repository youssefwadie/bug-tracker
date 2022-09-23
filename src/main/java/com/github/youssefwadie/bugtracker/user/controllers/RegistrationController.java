package com.github.youssefwadie.bugtracker.user.controllers;

import com.github.youssefwadie.bugtracker.model.RegistrationRequest;
import com.github.youssefwadie.bugtracker.user.service.RegistrationService;
import com.github.youssefwadie.bugtracker.util.SimpleResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .setMessage("checkout your mail box").build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);

    }


    @GetMapping("confirm")
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token) {
        try {
            String message = registrationService.confirmToken(token);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException ex) {
            SimpleResponseBody responseBody = SimpleResponseBody.builder(HttpStatus.BAD_REQUEST).setMessage(ex.getMessage()).build();
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
