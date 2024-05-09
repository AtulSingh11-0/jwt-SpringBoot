package com.example.jwt.Security;

import com.example.jwt.Config.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

//  extract the username from the token
  public String extractUsername ( String token ) {
    return extractClaim(token, Claims::getSubject);
  }

//  generate the token
  public String generateToken ( UserDetails userDetails ) {
    return generateToken(new HashMap<>(), userDetails);
  }

//  generate the token with extra claims
  public String generateToken (
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
    return Jwts.builder()
        .claims(extraClaims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + AppConstants.JWT_TOKEN_VALIDITY))
        .signWith(getSecretKey())
        .compact();
  }

//  validate the token
  public boolean validateToken ( String token, UserDetails userDetails ) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

//  check if the token is expired
  private boolean isTokenExpired ( String token ) {
    return extractExpiration(token).before(new Date());
  }

//  extract the expiration date from the token
  private Date extractExpiration ( String token ) {
    return extractClaim(token, Claims::getExpiration);
  }

  // extract the claim from the token
  public <T> T extractClaim ( String token, Function<Claims, T> claimsResolver) {
    final Claims claim = extractAllClaims(token);
    return claimsResolver.apply(claim);
  }

//  extract all claims from the token
  private Claims extractAllClaims ( String token ) {
    return Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

//  get the secret key
  private SecretKey getSecretKey () {
    String SECRET_KEY = "b3a6f7d2e5918c4b7a8d96e2f5c0b3e2a6f7d2e5918c4b7a8d96e2f5c0b3e2a6f7d2e5918c4b7a8d96e2f5c0b3e2a6f7d2e5918c4b7a8d96e2f5c0b3e2";
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
