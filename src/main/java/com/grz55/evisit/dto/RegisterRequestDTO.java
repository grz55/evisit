package com.grz55.evisit.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

  @Email(message = "Email should be valid") @NotBlank(message = "Email cannot be empty") private String email;

  private String password;
  private String firstName;
  private String lastName;
  private String role;
}
