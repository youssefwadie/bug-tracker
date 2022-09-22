package com.github.youssefwadie.bugtracker.security.filters;

import com.github.youssefwadie.bugtracker.security.BugTrackerUserDetails;
import com.github.youssefwadie.bugtracker.security.JwtService;
import com.github.youssefwadie.bugtracker.security.TokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JWTGeneratorFilter extends OncePerRequestFilter {
    private final TokenProperties tokenProperties;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        BugTrackerUserDetails userDetails = (BugTrackerUserDetails) (authentication.getPrincipal());

        String accessToken = jwtService.generateAccessToken(userDetails.getUser());
        ResponseCookie cookie = ResponseCookie.from(tokenProperties.getAccessTokenCookieName(), accessToken)
                .sameSite(Cookie.SameSite.LAX.attributeValue())
                .secure(true)
                .httpOnly(true)
                .maxAge(tokenProperties.getAccessTokenLifeTime())
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("api/v1/users/login");
    }
}
