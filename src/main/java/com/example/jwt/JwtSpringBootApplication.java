package com.example.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
@SpringBootApplication
public class JwtSpringBootApplication {

  public static void main ( String[] args ) {
    SpringApplication.run(JwtSpringBootApplication.class, args);
    System.out.println("Hello jwt!");
  }

  //  secured endpoint, needs authentication
  @GetMapping ("/demo")
  public ResponseEntity< String > hello () {
    return ResponseEntity.ok("Hello from a secured endpoint!");
  }

}
