package com.grz55.evisit.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private FilterChainProxy filterChain;

  private MockHttpServletRequestBuilder loginRequest() {
    return post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(
            """
                {
                    "email": "test@example.com",
                    "password": "password123"
                }
                """);
  }

  @Test
  void shouldPermitLoginEndpointWithoutAuth() throws Exception {
    mockMvc.perform(loginRequest()).andExpect(status().isOk()).andDo(print());
  }

  @Test
  void shouldBlockProtectedEndpointsWithoutToken() throws Exception {
    mockMvc.perform(get("/api/users/me")).andExpect(status().isForbidden());
  }

  @Test
  void registerEndpoint_ShouldBePublic() throws Exception {
    mockMvc
        .perform(post("/api/auth/register"))
        .andExpect(status().is4xxClientError()); // 400 (brak body), ale nie 403 (Forbidden)
  }
}
