package com.grz55.evisit.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.grz55.evisit.entity.UserEntity;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
// TODO
class UserTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  void user_WithInvalidEmail_ShouldFailValidation() {
    UserEntity user =
        UserEntity.builder()
            .email("invalid-email")
            .password("password")
            .firstName("Jan")
            .lastName("Kowalski")
            .role("PATIENT")
            .build();

    var violations = validator.validate(user);
    assertFalse(violations.isEmpty());
  }
}
