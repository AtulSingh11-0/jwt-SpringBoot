package com.example.jwt.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/megatronix/paridhi")
public class DemoController {

//  unsecured endpoint, no need for authentication
  @GetMapping("/hello")
  public ResponseEntity< String > unsecuredHello () {
    return ResponseEntity.ok("Hello from an unsecured endpoint!");
  }

}
