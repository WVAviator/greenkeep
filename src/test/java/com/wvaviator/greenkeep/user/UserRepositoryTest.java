package com.wvaviator.greenkeep.user;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User testUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build();
        User savedUser = userRepository.save(testUser);

        userRepository.flush();

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
    }

    @Test
    void testSaveUserFirstNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = User.builder()
                    .firstName("A".repeat(51))
                    .lastName("Doe")
                    .email("john@doe.com")
                    .build();
            User savedUser = userRepository.save(testUser);

            userRepository.flush();
        });
    }

    @Test
    void testSaveUserFirstNameTooShort() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = User.builder()
                    .firstName("")
                    .lastName("Doe")
                    .email("john@doe.com")
                    .build();
            User savedUser = userRepository.save(testUser);

            userRepository.flush();
        });
    }

    @Test
    void testSaveUserFirstNameMissing() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = User.builder()
                    .lastName("Doe")
                    .email("john@doe.com")
                    .build();
            User savedUser = userRepository.save(testUser);

            userRepository.flush();
        });
    }

    @Test
    void testSaveUserLastNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = User.builder()
                    .firstName("John")
                    .lastName("A".repeat(51))
                    .email("john@doe.com")
                    .build();
            User savedUser = userRepository.save(testUser);

            userRepository.flush();
        });
    }

    @Test
    void testSaveUserLastNameTooShort() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = User.builder()
                    .firstName("John")
                    .lastName("D")
                    .email("john@doe.com")
                    .build();
            User savedUser = userRepository.save(testUser);

            userRepository.flush();
        });
    }

    @Test
    void testSaveUserLastNameMissing() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = User.builder()
                    .firstName("John")
                    .email("john@doe.com")
                    .build();

            User savedUser = userRepository.save(testUser);

            userRepository.flush();
        });
    }

    @Test
    void testSaveUserEmailInvalid() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("johndoe")
                    .build();
            User savedUser = userRepository.save(testUser);

            userRepository.flush();
        });
    }

    @Test
    void testSaveUserEmailAlreadyExists() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            User testUser = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john@doe.com")
                    .build();
            User savedUser = userRepository.save(testUser);

            User testUser2 = User.builder()
                    .firstName("Jane")
                    .lastName("Doe")
                    .email("john@doe.com")
                    .build();

            User savedUser2 = userRepository.save(testUser2);

            userRepository.flush();
        });
    }

    @Test
    void testSaveUserMissingEmail() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .build();
            User savedUser = userRepository.save(testUser);

            userRepository.flush();
        });
    }

}
