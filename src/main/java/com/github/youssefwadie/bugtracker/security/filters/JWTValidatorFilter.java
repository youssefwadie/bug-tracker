package com.github.youssefwadie.bugtracker.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.service.BugTrackerUserDetails;
import com.github.youssefwadie.bugtracker.security.service.JwtService;
import com.github.youssefwadie.bugtracker.security.TokenProperties;
import com.github.youssefwadie.bugtracker.util.SimpleResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RequiredArgsConstructor
@Slf4j
public class JWTValidatorFilter extends OncePerRequestFilter {

    private final TokenProperties tokenProperties;
    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Cookie accessCookie = getAccessTokenCookie(request.getCookies());
        if (accessCookie == null) {
            filterChain.doFilter(request, response);
            return;
        }


        String jwt = accessCookie.getValue();
        if (jwt != null) {
            try {
                User user = jwtService.parseUser(jwt);
                BugTrackerUserDetails userDetails = new BugTrackerUserDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            } catch (Exception e) {
                log.error("Error while logging in: {}", e.getMessage());
                SimpleResponseBody simpleResponseBody = SimpleResponseBody
                        .builder(HttpStatus.UNAUTHORIZED)
                        .setMessage(e.getMessage()).build();

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                OutputStream responseOutputStream = response.getOutputStream();
                new ObjectMapper().writeValue(responseOutputStream, simpleResponseBody);
                responseOutputStream.close();

                throw new BadCredentialsException(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private Cookie getAccessTokenCookie(Cookie[] cookies) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(tokenProperties.getAccessTokenCookieName())) return cookie;
        }
        return null;
    }

    private boolean hasAccessTokenCookie(Cookie[] cookies) {
        if (cookies == null) return false;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(tokenProperties.getAccessTokenCookieName())) return true;
        }
        return false;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !hasAccessTokenCookie(request.getCookies());
    }
}
