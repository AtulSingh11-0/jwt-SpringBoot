package com.example.jwt.Config;

import com.example.jwt.Security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  @Autowired
  private final JwtAuthFilter jwtAuthFilter;

  @Autowired
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain ( HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .csrf(
            AbstractHttpConfigurer::disable
        )
        .authorizeHttpRequests(
            auth -> auth
                .requestMatchers("/api/v1/auth/**") // permit all for auth endpoints
                .permitAll()
                .requestMatchers("/api/v1/unsecured/**") // permit all for unsecured endpoints
                .permitAll()
                .anyRequest()
                .authenticated()
        ).sessionManagement(
            session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();

  }
}
