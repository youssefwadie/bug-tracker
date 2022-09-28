package com.github.youssefwadie.bugtracker.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.youssefwadie.bugtracker.security.service.AuthService;
import com.github.youssefwadie.bugtracker.security.service.BugTrackerUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccessTokenGeneratorFilter extends OncePerRequestFilter {
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        BugTrackerUserDetails userDetails = (BugTrackerUserDetails) (authentication.getPrincipal());


        ResponseCookie cookie = authService.generateAccessTokenCookie(userDetails.getUser());
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/api/v1/users/login");
    }
}
