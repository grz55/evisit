package com.grz55.evisit.auth;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.grz55.evisit.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;

@SpringBootTest
@ActiveProfiles("test")
class AuthPerformanceTest {

  @Autowired private JWTUtils jwtUtils;

  @Test
  void tokenGenerationShouldBeFast() {
    StopWatch stopWatch = new StopWatch();

    stopWatch.start();
    String token = jwtUtils.generateJwtToken("test@example.com");
    stopWatch.stop();

    assertTrue(stopWatch.getTotalTimeMillis() < 100);
    assertNotNull(token);
  }
}
