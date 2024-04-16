package com.example.jwt.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  @Autowired
  private final JwtService jwtService;

  @Autowired
  private final UserDetailsService userDetailsService;

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

//    extract the jwt token from the Authorization header
    jwt = authorizationHeader.substring(7);
//    extract the username from the jwt token
    userEmail = jwtService.extractUsername(jwt);

//    check if the user email is not null and the user is not already authenticated
    if( userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

//      validate the jwt token
      if ( jwtService.validateToken(jwt, userDetails) ) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken (
            userDetails,
            null,
            userDetails.getAuthorities()
        );
//        set the user details in the authentication token
        authenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
//        set the authentication token in the security context
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }

      filterChain.doFilter(request, response);
    }
  }

}
