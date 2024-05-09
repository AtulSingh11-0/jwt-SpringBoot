package com.example.jwt.auth.Controller;

import com.example.jwt.Model.User;
import com.example.jwt.auth.Response.AuthenticationResponse;
import com.example.jwt.auth.Service.AuthenticationService;
import com.example.jwt.exception.AdminNotFoundException;
import com.example.jwt.exception.EmailAlreadyExistsException;
import com.example.jwt.exception.EmailNotVerifiedException;
import com.example.jwt.exception.UserNotFoundException;
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
public CompletableFuture<ResponseEntity<AuthenticationResponse>> register(
    @RequestBody User request) {
  return authenticationService.register(request)
      .thenApply(ResponseEntity::ok)
      .exceptionally(ex -> {
        Throwable cause = ex.getCause();
        String errorMessage = cause != null ? cause.getMessage() : "Unknown error occurred";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse(errorMessage));
      });
}

  @PostMapping("/authenticate")
  public CompletableFuture<ResponseEntity<AuthenticationResponse>> login(
      @RequestBody User request) {
    return authenticationService.authenticate(request)
        .thenApply(ResponseEntity::ok)
        .exceptionally(ex -> {
          Throwable cause = ex.getCause();
          String errorMessage = cause != null ? cause.getMessage() : "Unknown error occurred";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse(errorMessage));
        });
  }

}
