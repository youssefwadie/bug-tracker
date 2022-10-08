package com.github.youssefwadie.bugtracker.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.youssefwadie.bugtracker.security.services.AuthService;
import com.github.youssefwadie.bugtracker.security.services.BugTrackerUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AccessTokenGeneratorFilter extends OncePerRequestFilter {
    private final AuthService authService;

    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        BugTrackerUserDetails userDetails = (BugTrackerUserDetails) (authentication.getPrincipal());

        if (!userDetails.isEmailVerified()) {
            SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            final Map<String, Object> responseBody = new LinkedHashMap<>() {{
                put("timestamp", LocalDateTime.now().toString());
                put("status", HttpStatus.UNAUTHORIZED.value());
                put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
                put("message", "verify your email");
            }};

            final String json = mapper.writeValueAsString(responseBody);
            response.getWriter().println(json);
            response.flushBuffer();
            return;
        }

        ResponseCookie cookie = authService.generateAccessTokenCookie(userDetails.getUser());
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/api/v1/users/login");
    }

}
