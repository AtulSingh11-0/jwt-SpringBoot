package com.example.jwt.auth.Controller;

import com.example.jwt.auth.AuthenticationResponse;
import com.example.jwt.auth.AuthenticationRequest;
import com.example.jwt.auth.RegisterRequest;
import com.example.jwt.auth.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  @Autowired
  private final AuthenticationService authenticationService;

//  register the user
  @PostMapping("/register")
  public CompletableFuture< ResponseEntity< AuthenticationResponse > > register (
      @RequestBody RegisterRequest request
  ) {
    return authenticationService
        .register(request)
        .thenApply(ResponseEntity::ok)
        .exceptionally(ex -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
  }

//  authenticate the user, basically login route
  @PostMapping("/authenticate")
  public CompletableFuture< ResponseEntity< AuthenticationResponse > > register (
      @RequestBody AuthenticationRequest request
  ) {
    return authenticationService
        .authenticate(request)
        .thenApply(ResponseEntity::ok)
        .exceptionally(ex -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }

}
