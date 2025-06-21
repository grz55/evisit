package com.grz55.evisit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "Email is required") @Email(message = "Email should be valid") @Size(max = 255, message = "Email cannot exceed 255 characters") @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "Password is required") @Size(min = 8, max = 100, message = "Password must be 8-100 characters long") @Pattern(
      regexp =
          "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=\\S+$).{8,}$",
      message = "Password must contain: 1 digit, 1 lowercase, 1 uppercase, 1 special character")
  @Column(nullable = false)
  private String password;

  @NotBlank(message = "First name is required") @Size(min = 2, max = 50, message = "First name must be 2-50 characters long") @Pattern(
      regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+([\\s'-][a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+)*$",
      message = "First name can only contain letters and hyphens/apostrophes")
  @Column(nullable = false, length = 50)
  private String firstName;

  @NotBlank(message = "Last name is required") @Size(min = 2, max = 50, message = "Last name must be 2-50 characters long") @Pattern(
      regexp = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+([\\s'-][a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+)*$",
      message = "Last name can only contain letters and hyphens/apostrophes")
  @Column(nullable = false, length = 50)
  private String lastName;

  @NotBlank(message = "Role is required") @Pattern(regexp = "^(PATIENT|DOCTOR|ADMIN)$", message = "Role must be PATIENT, DOCTOR or ADMIN")
  @Column(nullable = false, length = 20)
  private String role; // np. "PATIENT", "DOCTOR", "ADMIN"
}
