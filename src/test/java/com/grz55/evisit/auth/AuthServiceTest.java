package com.grz55.evisit.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.grz55.evisit.dto.RegisterRequestDTO;
import com.grz55.evisit.entity.UserEntity;
import com.grz55.evisit.repository.UserRepository;
import com.grz55.evisit.service.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

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
}
