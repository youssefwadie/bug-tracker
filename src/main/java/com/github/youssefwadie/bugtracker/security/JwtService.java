package com.github.youssefwadie.bugtracker.security;

import com.github.youssefwadie.bugtracker.model.Role;
import com.github.youssefwadie.bugtracker.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

import static com.github.youssefwadie.bugtracker.security.SecurityConstants.*;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final TokenProperties tokenProperties;

    public User parseUser(String jwt) {
        Assert.notNull(jwt, "jwt should not be null!");
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(this.tokenProperties.getSecretKey()).build()
                .parseClaimsJws(jwt)
                .getBody();

        Long id = claims.get(USER_ID_CLAIM_NAME, Long.class);
        String email = claims.getSubject();
        String name = claims.get(USER_NAME_CLAIM_NAME, String.class);
        Role userRole = Role.valueOf(claims.get(USER_ROLES_CLAIM_NAME, String.class));

        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setRole(userRole);
        user.setName(name);
        return user;
    }


    public String generateAccessToken(final User user) {
        Assert.notNull(user, "user must not be null!");

        Date now = new Date();
        Date accessTokenExpirationDate = new Date(now.getTime() + tokenProperties.getAccessTokenLifeTime());
        return Jwts.builder()
                .setIssuer("Bug Tracker")
                .setSubject(user.getEmail())
                .claim(USER_ID_CLAIM_NAME, user.getId())
                .claim(USER_NAME_CLAIM_NAME, user.getName())
                .claim(USER_ROLES_CLAIM_NAME, user.getRole())
                .setIssuedAt(now)
                .setExpiration(accessTokenExpirationDate)
                .signWith(tokenProperties.getSecretKey())
                .compact();
    }
}
