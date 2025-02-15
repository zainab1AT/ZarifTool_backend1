package com.project.physio_backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import com.project.physio_backend.security.jwt.AuthEntryPointJwt;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {
    // http
    // .csrf(csrf -> csrf.disable()) // Use the new csrf(Customizer) method
    // .authorizeHttpRequests(auth -> auth
    // .requestMatchers("/api/auth/signup").permitAll() // Allow signup and signin
    // .anyRequest().authenticated())
    // .sessionManagement(sess ->
    // sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    // .exceptionHandling(ex -> ex.authenticationEntryPoint(new
    // AuthEntryPointJwt()));

    // return http.build();
    // }
}

