package com.example.jwt.Config;

import com.example.jwt.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

  @Autowired
  private UserRepository userRepository;

//  This method is used to load the user from the database
  @Bean
  public UserDetailsService userDetailsService () {
    return email -> userRepository
        .findByEmail(email)
        .orElseThrow( () -> new UsernameNotFoundException("User not found") );
  }

//  This method is used to authenticate the user
  @Bean
  public AuthenticationProvider authenticationProvider () {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

//  This method is used to get the authentication manager
  @Bean
  public AuthenticationManager authenticationManager ( AuthenticationConfiguration authenticationConfiguration ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

//  encodes the password with BCryptPasswordEncoder before saving it to the database
  @Bean
  public PasswordEncoder passwordEncoder () {
    return new BCryptPasswordEncoder();
  }

}
