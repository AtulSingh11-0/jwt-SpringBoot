package com.example.jwt.auth.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// This class is used to send the JWT token back to the client
public class AuthenticationResponse {

  private String jwt;
}
