package com.grz55.evisit.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grz55.evisit.utils.JWTUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@Disabled
// TODO when any authorized endpoint implemented
class JwtAuthFilterTest {

  @Autowired private JWTUtils jwtUtils;

  @Autowired private MockMvc mockMvc;

  @Test
  void shouldSetAuthenticationForValidToken() throws Exception {
    String token = jwtUtils.generateJwtToken("test@example.com");

    mockMvc
        .perform(get("/api/users/me").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
  }

  @Test
  void shouldRejectInvalidToken() throws Exception {
    mockMvc
        .perform(get("/api/users/me").header("Authorization", "Bearer invalid.token"))
        .andExpect(status().isForbidden());
  }
}
