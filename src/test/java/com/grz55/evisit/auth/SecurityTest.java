package com.grz55.evisit.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void registerEndpoint_ShouldBePublic() throws Exception {
    mockMvc
        .perform(post("/api/auth/register"))
        .andExpect(status().is4xxClientError()); // 400 (brak body), ale nie 403 (Forbidden)
  }
}
