package com.example.jwt.auth.Request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// This class is used to receive the email and password from the client.
public class AuthenticationRequest {

  private String email;
  private String password;
}
