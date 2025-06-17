package com.grz55.evisit.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
// TODO
class AuthorizationControllerIT {

  @Autowired private MockMvc mockMvc;

  @Test
  void register_WithValidData_ShouldReturnOk() throws Exception {
    String requestBody =
        """
            {
                "email": "test@example.com",
                "password": "password123",
                "firstName": "Jan",
                "lastName": "Kowalski",
                "role": "PATIENT"
            }
            """;

    mockMvc
        .perform(post("/api/auth/register").contentType("application/json").content(requestBody))
        .andExpect(status().isOk());
  }

  @Test
  void register_WithInvalidEmail_ShouldReturnBadRequest() throws Exception {
    String invalidRequest =
        """
        {
            "email": "invalid-email",
            "password": "pass",
            "firstName": "",
            "lastName": "",
            "role": "INVALID_ROLE"
        }
        """;

    mockMvc
        .perform(post("/api/auth/register").contentType("application/json").content(invalidRequest))
        .andExpect(status().isBadRequest());
  }
}
