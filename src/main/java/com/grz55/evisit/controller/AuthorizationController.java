package com.grz55.evisit.controller;

import com.grz55.evisit.dto.AuthResponseDTO;
import com.grz55.evisit.dto.LoginRequestDTO;
import com.grz55.evisit.dto.RegisterRequestDTO;
import com.grz55.evisit.service.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {

  private final AuthorizationService authService;

  @PostMapping("/register")
  public void register(@Valid @RequestBody RegisterRequestDTO request) {
    authService.register(request);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
    AuthResponseDTO response = authService.authenticateUser(request);
    return ResponseEntity.ok(response);
  }
}
