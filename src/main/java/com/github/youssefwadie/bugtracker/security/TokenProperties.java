package com.github.youssefwadie.bugtracker.security;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.token")
public class TokenProperties {
    private String key;
    private long accessTokenLifeTime;

    private SecretKey secretKey;


    private String accessTokenCookieName;

    public TokenProperties() {
        this.key = "jxgE#*($#Qe_XH!uPq8Vdby@YFNkANd^u3dQ53YU%n4B";
        this.accessTokenLifeTime = 604_800; // one week (in seconds)
        this.secretKey = null;
        this.accessTokenCookieName = "access-token";
    }

    @PostConstruct
    void init() {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

}
