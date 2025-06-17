package com.grz55.evisit.service;

import com.grz55.evisit.dto.RegisterRequestDTO;
import com.grz55.evisit.entity.UserEntity;
import com.grz55.evisit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

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
}
