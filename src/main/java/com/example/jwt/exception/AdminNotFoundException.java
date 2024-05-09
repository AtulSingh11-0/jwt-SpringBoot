package com.example.jwt.exception;

public class AdminNotFoundException extends RuntimeException {
  public AdminNotFoundException(String message) {
    super(message);
  }
}
