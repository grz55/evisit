package com.grz55.evisit.auth;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.grz55.evisit.entity.UserEntity;
import com.grz55.evisit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  @Test
  void saveUser_ShouldPersistInDatabase() {
    UserEntity user =
        UserEntity.builder()
            .email("test@example.com")
            .password("encodedPassword")
            .firstName("Jan")
            .lastName("Kowalski")
            .role("PATIENT")
            .build();

    UserEntity savedUser = userRepository.save(user);
    assertNotNull(savedUser.getId());
  }
}
