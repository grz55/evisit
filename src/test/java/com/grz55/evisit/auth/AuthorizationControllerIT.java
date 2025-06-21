package com.grz55.evisit.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grz55.evisit.dto.AuthResponseDTO;
import com.grz55.evisit.service.AuthorizationService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorizationControllerIT {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private AuthorizationService authService;

  @Test
  void register_WithValidData_ShouldReturnOk() throws Exception {
    String requestBody =
        """
            {
                "email": "test@example.com",
                "password": "StrongPass123!",
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
  void register_WithInvalidEmail_ShouldReturn400() throws Exception {
    mockMvc
        .perform(
            post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                {
                    "email": "invalid-email",
                    "password": "ValidPass123!"
                }"""))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnTokenOnValidLogin() throws Exception {
    // Given
    AuthResponseDTO mockResponse =
        new AuthResponseDTO("token", "test@example.com", "USER", UUID.randomUUID());
    when(authService.authenticateUser(any())).thenReturn(mockResponse);

    // When/Then
    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "email": "test@example.com",
                        "password": "StrongPass123!"
                    }
                    """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists());
  }

  @Test
  void shouldReturn401OnInvalidCredentials() throws Exception {
    when(authService.authenticateUser(any()))
        .thenThrow(new BadCredentialsException("Invalid credentials"));

    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                {
                    "email": "test@example.com",
                    "password": "abc"
                }
                """))
        .andExpect(status().isUnauthorized());
  }
}
