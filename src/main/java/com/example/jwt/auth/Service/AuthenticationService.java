package com.example.jwt.auth.Service;

import com.example.jwt.Model.Admin;
import com.example.jwt.Model.User;
import com.example.jwt.Repository.AdminRepository;
import com.example.jwt.Repository.UserRepository;
import com.example.jwt.Security.JwtService;
import com.example.jwt.auth.Response.AuthenticationResponse;
import com.example.jwt.auth.Request.AuthenticationRequest;
import com.example.jwt.exception.AdminNotFoundException;
import com.example.jwt.exception.EmailAlreadyExistsException;
import com.example.jwt.exception.EmailNotVerifiedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private AdminRepository adminRepository;

  //  register the user and return the jwt token
  public CompletableFuture< AuthenticationResponse > register ( User request ) {
    if( request.isEmailVerified()) {
      Optional< Admin > admin = adminRepository.findByUsername(request.getEmail());
      if( admin.isPresent()) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if( userOptional.isPresent()) {
          throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ADMIN");
        user.setEmailVerified(true);

        userRepository.save(user);

        return CompletableFuture.completedFuture(
            response(user)
        );
      } else {
        throw new AdminNotFoundException("Admin not found");
      }
    } else {
      throw new EmailNotVerifiedException("Email not verified");
    }
  }

//  authenticate the user and return the jwt token
  public CompletableFuture< AuthenticationResponse > authenticate ( User request ) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );

    User user = userRepository
        .findByEmail(request.getEmail())
        .orElseThrow( () -> new UsernameNotFoundException("Admin not found"));

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
