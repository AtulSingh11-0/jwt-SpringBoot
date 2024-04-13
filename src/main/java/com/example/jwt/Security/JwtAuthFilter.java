package com.example.jwt.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  @Autowired
  private final JwtService jwtService;

  @Override
  protected void doFilterInternal (
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
      ) throws ServletException, IOException {

//    get the value of the Authorization header
    final String authorizationHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;

//    check if the request has a header with the key Authorization
    if( authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    jwt = authorizationHeader.substring(7);
    userEmail = jwtService.extractUsername(jwt);

  }

}
