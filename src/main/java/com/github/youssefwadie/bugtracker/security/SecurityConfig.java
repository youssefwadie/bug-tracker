package com.github.youssefwadie.bugtracker.security;

import com.github.youssefwadie.bugtracker.security.filters.JWTGeneratorFilter;
import com.github.youssefwadie.bugtracker.security.filters.JWTValidatorFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
public class SecurityConfig {
    private final TokenProperties tokenProperties;
    private final JwtService jwtService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.cors().configurationSource(request -> {
//            CorsConfiguration configuration = new CorsConfiguration();
//            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//            configuration.setAllowCredentials(true);
//            configuration.setAllowedHeaders(Collections.singletonList("*"));
//            configuration.setAllowedMethods(Collections.singletonList("*"));
//            configuration.setExposedHeaders(List.of("Authorization"));
//            configuration.setMaxAge(3600L);
//            return configuration;
//        });

        http.csrf().disable();
        http.authorizeRequests(request -> {
            request.antMatchers("/api/v1/users/**").authenticated();
            request.antMatchers("/api/v1/register/**").permitAll();

        });
        http.addFilterBefore(jwtValidatorFilter(), BasicAuthenticationFilter.class);
        http.addFilterAfter(jwtGeneratorFilter(), BasicAuthenticationFilter.class);
        http.httpBasic();
        return http.build();
    }

    Filter jwtValidatorFilter() {
        return new JWTValidatorFilter(tokenProperties, jwtService);
    }

    Filter jwtGeneratorFilter() {
        return new JWTGeneratorFilter(tokenProperties, jwtService);
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
