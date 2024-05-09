package com.example.jwt.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;

@Data
@Async
@Entity
@NoArgsConstructor
public class Admin {

  @Id
  @GeneratedValue (strategy = GenerationType.AUTO)
  private Long id;
  private String username;
}
