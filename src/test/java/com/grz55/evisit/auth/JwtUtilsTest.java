package com.grz55.evisit.auth;

import static org.junit.jupiter.api.Assertions.*;

import com.grz55.evisit.utils.JWTUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

  private JWTUtils jwtUtils;
  private final String secret = "c2VjdXJlLXNlY3JldC1rZXktZm9yLWp3dC1zaWduaW5nLTI1Ni1iaXRz";
  private final int expirationMs = 86400000;

  @BeforeEach
  void setUp() {
    jwtUtils = new JWTUtils(secret, expirationMs);
  }

  @Test
  void shouldGenerateValidToken() {
    String token = jwtUtils.generateJwtToken("test@example.com");
    assertNotNull(token);
    assertEquals("test@example.com", jwtUtils.getEmailFromJwtToken(token));
  }

  @Test
  void shouldThrowOnInvalidToken() {
    assertThrows(JwtException.class, () -> jwtUtils.getEmailFromJwtToken("invalid.token.here"));
  }

  @Test
  void shouldReturnFalseForExpiredToken() {
    String expiredToken =
        Jwts.builder()
            .subject("test@example.com")
            .issuedAt(new Date(System.currentTimeMillis() - 10000))
            .expiration(new Date(System.currentTimeMillis() - 5000))
            .signWith(jwtUtils.getKey())
            .compact();

    assertFalse(jwtUtils.validateJwtToken(expiredToken));
  }
}
