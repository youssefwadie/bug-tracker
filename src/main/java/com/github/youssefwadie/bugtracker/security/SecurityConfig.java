package com.github.youssefwadie.bugtracker.security;

import com.github.youssefwadie.bugtracker.security.filters.JWTGeneratorFilter;
import com.github.youssefwadie.bugtracker.security.filters.JWTValidatorFilter;
import com.github.youssefwadie.bugtracker.security.interceptors.UserContextInterceptor;
import com.github.youssefwadie.bugtracker.security.service.BugTrackerAuthenticationEntryPoint;
import com.github.youssefwadie.bugtracker.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    @Autowired
    SecurityFilterChain filterChain(
            final HttpSecurity http,
            final BugTrackerAuthenticationEntryPoint authenticationEntryPoint,
            final JwtService jwtService,
            final TokenProperties tokenProperties
    ) throws Exception {
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
        http.authorizeRequests(auth -> {
            auth.antMatchers("/api/v1/admin/**").hasRole("ADMIN");
            auth.antMatchers("/api/v1/projects/**").authenticated();
        	auth.antMatchers("/api/v1/tickets/**").authenticated();
            auth.antMatchers("/api/v1/users/login").authenticated();
            auth.antMatchers("/api/v1/users/resend").permitAll();
            auth.antMatchers("/api/v1/register/**").permitAll();
        });

        http.httpBasic().authenticationEntryPoint(authenticationEntryPoint);

        http.addFilterBefore(jwtValidatorFilter(jwtService, tokenProperties), BasicAuthenticationFilter.class);
        http.addFilterAfter(jwtGeneratorFilter(jwtService, tokenProperties), BasicAuthenticationFilter.class);
        return http.build();
    }

    Filter jwtValidatorFilter(JwtService jwtService, TokenProperties tokenProperties) {
        return new JWTValidatorFilter(tokenProperties, jwtService);
    }

    Filter jwtGeneratorFilter(JwtService jwtService, TokenProperties tokenProperties) {
        return new JWTGeneratorFilter(tokenProperties, jwtService);
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
