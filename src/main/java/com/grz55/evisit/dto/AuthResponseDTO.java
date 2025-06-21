package com.grz55.evisit.dto;

import java.util.UUID;

public record AuthResponseDTO(String token, String email, String role, UUID id) {}
