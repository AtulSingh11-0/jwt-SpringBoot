package com.example.jwt.auth.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
// This class is used to map the request body of the register endpoint
public class RegisterRequest {

  private String name;
  private String email;
  private String password;
}
