package com.example.jwt.auth.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
// This class is used to receive the email and password from the client.
public class AuthenticationRequest {

  private String email;
  private String password;
}
