package com.example.jwt.auth.Controller;

import com.example.jwt.Model.User;
import com.example.jwt.auth.Response.AuthenticationResponse;
import com.example.jwt.auth.Request.AuthenticationRequest;
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
      @RequestBody User request
  ) {
    return authenticationService
        .register(request)
        .thenApply(s -> {
          if ( s != null ) {
            return ResponseEntity.ok(s);
          } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(s);
          }
        })
        .exceptionally(ex -> {
//          System.out.println(ex.getMessage();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        });
  }

//  authenticate the user, basically login route
  @PostMapping("/authenticate")
  public CompletableFuture< ResponseEntity< AuthenticationResponse > > login (
      @RequestBody User request
  ) {
    return authenticationService
        .authenticate(request)
        .thenApply(s -> {
          if ( s != null ) {
            return ResponseEntity.ok(s);
          } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(s);
          }
        })
        .exceptionally(ex -> {
//          System.out.println(ex.getMessage();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        });
  }

}
