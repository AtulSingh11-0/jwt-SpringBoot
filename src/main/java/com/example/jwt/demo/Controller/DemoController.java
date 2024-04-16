package com.example.jwt.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

//  secured endpoint, needs authentication
  @GetMapping("/demo/hello")
  public ResponseEntity< String > hello () {
    return ResponseEntity.ok("Hello from a secured endpoint!");
  }

//  unsecured endpoint, no need for authentication
  @GetMapping("/unsecured/hello")
  public ResponseEntity< String > unsecuredHello () {
    return ResponseEntity.ok("Hello from an unsecured endpoint!");
  }

}
