package com.github.youssefwadie.bugtracker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.youssefwadie.bugtracker.security.TokenProperties;
import com.github.youssefwadie.bugtracker.security.filters.AccessTokenGeneratorFilter;
import com.github.youssefwadie.bugtracker.security.filters.AccessTokenValidatorFilter;
import com.github.youssefwadie.bugtracker.security.interceptors.UserContextInterceptor;
import com.github.youssefwadie.bugtracker.security.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.Collections;

import static com.github.youssefwadie.bugtracker.constants.ResponseConstants.TOTAL_COUNT_HEADER_NAME;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
public class SecurityConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final TokenProperties tokenProperties;

    @Bean
    @Autowired
    SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors().configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setExposedHeaders(Collections.singletonList(TOTAL_COUNT_HEADER_NAME));
            configuration.setMaxAge(3600L);
            return configuration;
        });


        http.csrf().disable();
        http.authorizeRequests(auth -> {
            auth.antMatchers("/api/v1/admin/**").hasRole("ADMIN");
            auth.antMatchers("/api/v1/users/login").authenticated();
            auth.antMatchers("/api/v1/users/role").authenticated();

            auth.antMatchers("/api/v1/projects/**", "/api/v1/tickets/**", "/api/v1/dashboard/**").hasAnyRole("ADMIN", "DEVELOPER");

            auth.antMatchers("/api/v1/register/**", "/api/v1/users/resend", "/api/v1/users/logged-in").permitAll();
            auth.antMatchers(HttpMethod.GET, "/actuator/**").permitAll();
            auth.anyRequest().authenticated();
        });

        http.httpBasic();

        http.addFilterBefore(jwtValidatorFilter(), BasicAuthenticationFilter.class);
        http.addFilterAfter(jwtGeneratorFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }

    Filter jwtValidatorFilter() {
        return new AccessTokenValidatorFilter(authService, tokenProperties);
    }

    Filter jwtGeneratorFilter() {
        return new AccessTokenGeneratorFilter(authService, new ObjectMapper());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextInterceptor());
    }
}
