package com.grz55.evisit.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.grz55.evisit.dto.AuthResponseDTO;
import com.grz55.evisit.dto.LoginRequestDTO;
import com.grz55.evisit.dto.RegisterRequestDTO;
import com.grz55.evisit.entity.UserEntity;
import com.grz55.evisit.repository.UserRepository;
import com.grz55.evisit.service.AuthorizationService;
import com.grz55.evisit.utils.JWTUtils;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @Mock private JWTUtils jwtUtils;

  @InjectMocks private AuthorizationService authService;

  @Test
  void register_ShouldEncodePasswordAndSaveUser() {
    // Given
    RegisterRequestDTO request =
        new RegisterRequestDTO("test@example.com", "password", "Jan", "Kowalski", "PATIENT");
    when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

    // When
    authService.register(request);

    // Then
    verify(passwordEncoder).encode("password");
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void shouldAuthenticateValidUser() {
    // Given
    UserEntity user =
        new UserEntity(
            UUID.randomUUID(), "test@example.com", "encodedPass", "John", "Smith", "User");
    when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
    when(passwordEncoder.matches("password123", "encodedPass")).thenReturn(true);
    when(jwtUtils.generateJwtToken("test@example.com")).thenReturn("mock-jwt-token");

    // When
    AuthResponseDTO response =
        authService.authenticateUser(new LoginRequestDTO("test@example.com", "password123"));

    // Then
    assertNotNull(response.token());
    assertEquals("test@example.com", response.email());
  }

  @Test
  void shouldThrowOnInvalidCredentials() {
    when(userRepository.findByEmail("wrong@example.com")).thenReturn(Optional.empty());

    assertThrows(
        BadCredentialsException.class,
        () ->
            authService.authenticateUser(new LoginRequestDTO("wrong@example.com", "password123")));
  }
}
