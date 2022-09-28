package com.github.youssefwadie.bugtracker.security.service;

import static com.github.youssefwadie.bugtracker.security.SecurityConstants.USER_ID_CLAIM_NAME;
import static com.github.youssefwadie.bugtracker.security.SecurityConstants.USER_NAME_CLAIM_NAME;
import static com.github.youssefwadie.bugtracker.security.SecurityConstants.USER_ROLES_CLAIM_NAME;

import java.util.Date;

import org.springframework.boot.web.server.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.youssefwadie.bugtracker.model.Role;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.TokenProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final TokenProperties tokenProperties;

    public ResponseCookie generateAccessTokenCookie(final User user) {
        Assert.notNull(user, "user should not be null!");
        String token = generateAccessToken(user);
        return accessTokenCookie(token, tokenProperties.getAccessTokenLifeTime());
    }

    public ResponseCookie removeAccessTokenCookie() {
        return accessTokenCookie("", 0);
    }

    public User parseUser(final String accessToken) {
        Assert.notNull(accessToken, "accessToken should not be null!");
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(this.tokenProperties.getSecretKey()).build()
                .parseClaimsJws(accessToken)
                .getBody();

        Long id = claims.get(USER_ID_CLAIM_NAME, Long.class);
        String email = claims.getSubject();
        String fullName = claims.get(USER_NAME_CLAIM_NAME, String.class);
        Role userRole = Role.valueOf(claims.get(USER_ROLES_CLAIM_NAME, String.class));

        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setRole(userRole);
        user.setFullName(fullName);
        return user;
    }


    private String generateAccessToken(final User user) {
        Date now = new Date();
        Date accessTokenExpirationDate = new Date(now.getTime() + tokenProperties.getAccessTokenLifeTime());
        return Jwts.builder()
                .setIssuer("Bug Tracker")
                .setSubject(user.getEmail())
                .claim(USER_ID_CLAIM_NAME, user.getId())
                .claim(USER_NAME_CLAIM_NAME, user.getFullName())
                .claim(USER_ROLES_CLAIM_NAME, user.getRole())
                .setIssuedAt(now)
                .setExpiration(accessTokenExpirationDate)
                .signWith(tokenProperties.getSecretKey())
                .compact();
    }


    private ResponseCookie accessTokenCookie(String token, long maxAge) {
        return ResponseCookie.from(tokenProperties.getAccessTokenCookieName(), token)
                .sameSite(Cookie.SameSite.LAX.attributeValue())
                .secure(true)
                .httpOnly(true)
                .maxAge(maxAge)
                .path("/api/v1")
                .build();
    }
}
