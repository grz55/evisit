package com.grz55.evisit.user;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.grz55.evisit.entity.UserEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  void shouldPassWithValidData() {
    UserEntity user = createValidUser();
    assertTrue(validator.validate(user).isEmpty());
  }

  @Test
  void shouldFailWithInvalidEmail() {
    UserEntity user = createValidUser().withEmail("invalid-email");
    assertValidationFails(user, "Email should be valid");
  }

  @Test
  void shouldFailWithWeakPassword() {
    UserEntity user = createValidUser().withPassword("weak");
    assertValidationFails(user, "Password must be 8-100 characters long");
  }

  @Test
  void shouldFailWithInvalidFirstName() {
    UserEntity user = createValidUser().withFirstName("J0hn");
    assertValidationFails(user, "First name can only contain letters");
  }

  @Test
  void shouldFailWithInvalidRole() {
    UserEntity user = createValidUser().withRole("INVALID_ROLE");
    assertValidationFails(user, "Role must be PATIENT, DOCTOR or ADMIN");
  }

  private UserEntity createValidUser() {
    return UserEntity.builder()
        .email("valid@example.com")
        .password("StrongPass123!")
        .firstName("Jan")
        .lastName("Kowalski")
        .role("PATIENT")
        .build();
  }

  private void assertValidationFails(UserEntity user, String expectedMessage) {
    Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains(expectedMessage)));
  }
}
