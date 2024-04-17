package com.example.jwt.auth.Service;

import com.example.jwt.Model.Role;
import com.example.jwt.Model.User;
import com.example.jwt.Repository.UserRepository;
import com.example.jwt.Security.JwtService;
import com.example.jwt.auth.Response.AuthenticationResponse;
import com.example.jwt.auth.Request.AuthenticationRequest;
import com.example.jwt.auth.Request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  @Autowired
  private final UserRepository userRepository;

  @Autowired
  private final PasswordEncoder passwordEncoder;

  @Autowired
  private final JwtService jwtService;

  @Autowired
  private final AuthenticationManager authenticationManager;

//  register the user and return the jwt token
  public CompletableFuture< AuthenticationResponse > register ( RegisterRequest request ) {
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.ADMIN);

    userRepository.save(user);

    return CompletableFuture.completedFuture(
        response(user)
    );
  }

//  authenticate the user and return the jwt token
  public CompletableFuture< AuthenticationResponse > authenticate ( AuthenticationRequest request ) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );

    User user = userRepository
        .findByEmail(request.getEmail())
        .orElseThrow( () -> new RuntimeException("User not found"));

    return CompletableFuture.completedFuture(
        response(user)
    );
  }

//  method to return an authentication response object containing the jwt token
  private AuthenticationResponse response ( User user ) {
    return AuthenticationResponse
        .builder()
        .jwt(jwtService.generateToken(user))
        .build();
  }

}
