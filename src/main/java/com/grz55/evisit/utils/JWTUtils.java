package com.grz55.evisit.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JWTUtils {

  @Getter private final SecretKey key;
  private final int jwtExpirationMs;
  private static final SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;

  public JWTUtils(
      @Value("${jwt.secret}") String secret, @Value("${jwt.expirationMs}") int jwtExpirationMs) {

    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    this.jwtExpirationMs = jwtExpirationMs;
  }

  public String generateJwtToken(String email) {
    return Jwts.builder()
        .subject(email)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(key, ALGORITHM)
        .compact();
  }

  public String getEmailFromJwtToken(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(authToken);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    } catch (JwtException e) {
      log.error("JWT validation error: {}", e.getMessage());
    }
    return false;
  }
}
