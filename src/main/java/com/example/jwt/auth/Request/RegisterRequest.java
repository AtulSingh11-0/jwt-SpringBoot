package com.example.jwt.auth.Request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// This class is used to map the request body of the register endpoint
public class RegisterRequest {

  private String name;
  private String email;
  private String password;
}
