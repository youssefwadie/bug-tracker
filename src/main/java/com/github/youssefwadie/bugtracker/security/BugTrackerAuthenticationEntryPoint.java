package com.github.youssefwadie.bugtracker.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BugTrackerAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final String realmName = "Realm";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + this.realmName + "\"");
        String message = HttpStatus.UNAUTHORIZED.getReasonPhrase();

        if (authException instanceof DisabledException) {
            message = "please verify your account";
        }
        response.sendError(HttpStatus.UNAUTHORIZED.value(), message);
    }
}
