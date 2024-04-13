package com.example.jwt.Security;

import com.example.jwt.Config.AppConstants;
import io.github.cdimascio.dotenv.Dotenv;
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

//  load the environment variables
  static Dotenv dotenv = Dotenv.load();
//  get the secret key from the environment variable
  private static final String SECRET_KEY = dotenv.get("SECRET_KEY");

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
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
