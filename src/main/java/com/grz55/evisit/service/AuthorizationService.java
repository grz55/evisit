package com.grz55.evisit.service;

import com.grz55.evisit.dto.AuthResponseDTO;
import com.grz55.evisit.dto.LoginRequestDTO;
import com.grz55.evisit.dto.RegisterRequestDTO;
import com.grz55.evisit.entity.UserEntity;
import com.grz55.evisit.repository.UserRepository;
import com.grz55.evisit.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTUtils jwtUtils;

  public void register(RegisterRequestDTO request) {
    var user =
        UserEntity.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .role(request.getRole())
            .build();
    userRepository.save(user);
  }

  public AuthResponseDTO authenticateUser(LoginRequestDTO request) {
    UserEntity user =
        userRepository
            .findByEmail(request.email())
            .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new BadCredentialsException("Invalid credentials");
    }

    String token = jwtUtils.generateJwtToken(user.getEmail());

    return new AuthResponseDTO(token, user.getEmail(), user.getRole(), user.getId());
  }
}
