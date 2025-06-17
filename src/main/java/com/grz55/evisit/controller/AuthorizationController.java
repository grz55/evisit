package com.grz55.evisit.controller;

import com.grz55.evisit.dto.RegisterRequestDTO;
import com.grz55.evisit.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
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
  public void register(@RequestBody RegisterRequestDTO request) {
    authService.register(request);
  }
}
