package com.github.youssefwadie.bugtracker.user.controller;

import com.github.youssefwadie.bugtracker.dto.user.UserDto;
import com.github.youssefwadie.bugtracker.dto.user.UserMapper;
import com.github.youssefwadie.bugtracker.model.ResendEmailRequest;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.UserContextHolder;
import com.github.youssefwadie.bugtracker.security.service.AuthService;
import com.github.youssefwadie.bugtracker.user.service.RegistrationService;
import com.github.youssefwadie.bugtracker.user.service.UserService;
import com.github.youssefwadie.bugtracker.util.SimpleResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserAuthController {
	private final UserMapper userMapper;
	private final RegistrationService registrationService;
	private final UserService userService;

	private final AuthService authService;

	@PostMapping("login")
	public ResponseEntity<UserDto> login() {
		UserDto userDto = userMapper.modelToDto(UserContextHolder.get());
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}

	@GetMapping(value = "logged-in")
	public ResponseEntity<Boolean> isLoggdIn() {
		boolean loggedIn = UserContextHolder.get() != null;
		return ResponseEntity.ok(loggedIn);
	}

	@PostMapping(value = "resend", produces = "application/json")
	public ResponseEntity<?> resendConfirmationToken(@RequestBody ResendEmailRequest resendEmailRequest) {
		if (resendEmailRequest.getEmail() == null) {
			return notFoundResponseEntity("an email must be provided");
		}

		Optional<User> userByEmailOptional = userService.findByEmail(resendEmailRequest.getEmail());
		if (userByEmailOptional.isEmpty()) {
			return notFoundResponseEntity("invalid email");
		}

		final User userByEmail = userByEmailOptional.get();
		if (userByEmail.isEmailVerified()) {
			final SimpleResponseBody responseBody = SimpleResponseBody.builder(HttpStatus.OK)
					.message("already confirmed").build();
			return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		}

		registrationService.resendConfirmationToken(userByEmail);
		final SimpleResponseBody responseBody = SimpleResponseBody.builder(HttpStatus.OK)
				.message("checkout your mail box").build();

		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}

	private ResponseEntity<SimpleResponseBody> notFoundResponseEntity(String message) {
		final SimpleResponseBody responseBody = SimpleResponseBody.builder(HttpStatus.BAD_REQUEST).message(message)
				.build();
		return ResponseEntity.badRequest().body(responseBody);
	}

	@PostMapping("logout")
	public ResponseEntity<String> logout() {
		ResponseCookie cookie = authService.removeAccessTokenCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("OK");
	}
}
