package com.wvaviator.greenkeep.user;

import com.wvaviator.greenkeep.exceptions.EmailExistsException;
import com.wvaviator.greenkeep.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@WithMockUser(authorities = "ADMIN")
class UserIntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testListUsers() {
        ResponseEntity<List<UserResponseDto>> response = userController.listUsers();
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback
    void testListUserEmptyListIfNone() {
        userRepository.deleteAll();
        ResponseEntity<List<UserResponseDto>> response = userController.listUsers();
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void testGetUser() {
        ResponseEntity<UserResponseDto> response = userController.getUser(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("John");
        assertThat(response.getBody().getLastName()).isEqualTo("Doe");
        assertThat(response.getBody().getEmail()).isEqualTo("john@doe.com");
    }

    @Test
    void testGetUserNotFound() {
        assertThrows(NotFoundException.class, () -> userController.getUser(2L));
    }

    @Test
    @Transactional
    @Rollback
    void testCreateUser() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@doe.com")
                .build();

        ResponseEntity<UserResponseDto> response = userController.createUser(userCreateDto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo(userCreateDto.getFirstName());
        assertThat(response.getBody().getLastName()).isEqualTo(userCreateDto.getLastName());
        assertThat(response.getBody().getEmail()).isEqualTo(userCreateDto.getEmail());

        User user = userRepository.findById(response.getBody().getId()).orElseThrow();
        assertThat(user.getFirstName()).isEqualTo(userCreateDto.getFirstName());
        assertThat(user.getLastName()).isEqualTo(userCreateDto.getLastName());
        assertThat(user.getEmail()).isEqualTo(userCreateDto.getEmail());
    }

    @Test
    void testCreateUserDuplicateEmail() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("john@doe.com")
                .build();

        assertThrows(EmailExistsException.class, () -> userController.createUser(userCreateDto));
    }

    @Test
    @Transactional
    @Rollback
    void testPutUpdateUser() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@doe.com")
                .build();

        ResponseEntity<UserResponseDto> response = userController.putUser(1L, userCreateDto);

        assertThat(response.getBody()).isNotNull();

        User user = userRepository.findById(1L).orElseThrow();

        assertThat(user.getFirstName()).isEqualTo(userCreateDto.getFirstName());
        assertThat(user.getLastName()).isEqualTo(userCreateDto.getLastName());
        assertThat(user.getEmail()).isEqualTo(userCreateDto.getEmail());
    }

    @Test
    @Transactional
    @Rollback
    void testPutCreateUser() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@doe.com")
                .build();

        ResponseEntity<UserResponseDto> response = userController.putUser(3L, userCreateDto);

        assertThat(response.getBody()).isNotNull();

        User user = userRepository.findById(response.getBody().getId()).orElseThrow();

        assertThat(user.getFirstName()).isEqualTo(userCreateDto.getFirstName());
        assertThat(user.getLastName()).isEqualTo(userCreateDto.getLastName());
        assertThat(user.getEmail()).isEqualTo(userCreateDto.getEmail());
    }

    @Test
    @Transactional
    @Rollback
    void testPatchUser() {
        UserPatchDto userPatchDto = UserPatchDto.builder()
                .firstName("Jane")
                .build();

        ResponseEntity<UserResponseDto> response = userController.patchUser(1L, userPatchDto);

        assertThat(response.getBody()).isNotNull();

        User user = userRepository.findById(1L).orElseThrow();

        assertThat(user.getFirstName()).isEqualTo(userPatchDto.getFirstName());
    }

    @Test
    void testPatchUserNotFound() {
        UserPatchDto userPatchDto = UserPatchDto.builder()
                .firstName("Jane")
                .build();

        assertThrows(NotFoundException.class, () -> userController.patchUser(2L, userPatchDto));
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteUser() {
        ResponseEntity<UserResponseDto> response = userController.deleteUser(1L);

        assertThat(response.getBody()).isNotNull();

        assertThrows(NotFoundException.class, () -> userController.getUser(1L));

        assertThat(userRepository.findById(1L)).isEmpty();
    }

    @Test
    void testDeleteUserNotFound() {
        assertThrows(NotFoundException.class, () -> userController.deleteUser(2L));
    }

}
