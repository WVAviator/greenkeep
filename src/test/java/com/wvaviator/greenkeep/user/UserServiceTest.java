package com.wvaviator.greenkeep.user;

import com.wvaviator.greenkeep.exceptions.EmailExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    UserCreateDto userCreateDto = UserCreateDto.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john@doe.com")
            .build();

    User user = User.builder()
            .id(1L)
            .firstName("John")
            .lastName("Doe")
            .email("john@doe.com")
            .build();

    UserResponseDto userResponseDto = UserResponseDto.builder()
            .id(1L)
            .firstName("John")
            .lastName("Doe")
            .email("john@doe.com")
            .build();

    @Test
    void testCreateUser() {
        when(userMapper.userCreateDtoToUser(any(UserCreateDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        UserResponseDto returnedUser = userService.createUser(userCreateDto);

        assertThat(returnedUser).usingRecursiveComparison().isEqualTo(userResponseDto);
        verify(userMapper, times(1)).userCreateDtoToUser(any(UserCreateDto.class));
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).userToUserResponseDto(any(User.class));
    }

    @Test
    void testCreateUserEmailExists() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        assertThrows(EmailExistsException.class, () -> userService.createUser(userCreateDto));
    }

    @Test
    void testListUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        List<UserResponseDto> returnedUsers = userService.listUsers();

        assertThat(returnedUsers).usingRecursiveComparison().isEqualTo(List.of(userResponseDto));
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).userToUserResponseDto(any(User.class));
    }

    @Test
    void testGetUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        UserResponseDto returnedUser = userService.getUser(1L).get();

        assertThat(returnedUser).usingRecursiveComparison().isEqualTo(userResponseDto);
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userMapper, times(1)).userToUserResponseDto(any(User.class));
    }

    @Test
    void testGetUserNotFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Optional<UserResponseDto> returnedUser = userService.getUser(1L);

        assertThat(returnedUser).isEmpty();
        verify(userRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testPutUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto returnedUser = userService.putUser(1L, userCreateDto).get();

        assertThat(returnedUser).usingRecursiveComparison().isEqualTo(userResponseDto);
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userMapper, times(1)).userToUserResponseDto(any(User.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testPutUserNotFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(userMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);
        when(userMapper.userCreateDtoToUser(any(UserCreateDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto returnedUser = userService.putUser(1L, userCreateDto).get();

        assertThat(returnedUser).usingRecursiveComparison().isEqualTo(userResponseDto);
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userMapper, times(1)).userToUserResponseDto(any(User.class));
        verify(userMapper, times(1)).userCreateDtoToUser(any(UserCreateDto.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testPutUserNotFoundButEmailExists() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        assertThrows(EmailExistsException.class, () -> userService.putUser(1L, userCreateDto));

        verify(userRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testPatchUser() {
        UserPatchDto userPatchDto = UserPatchDto.builder()
                .firstName("Jane")
                .build();

        User updatedUser = User.builder()
                .id(user.getId())
                .firstName("Jane")
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();

        UserResponseDto updatedUserResponseDto = UserResponseDto.builder()
                .id(user.getId())
                .firstName("Jane")
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponseDto(updatedUser)).thenReturn(updatedUserResponseDto);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserResponseDto returnedUser = userService.patchUser(1L, userPatchDto).get();

        assertThat(returnedUser).usingRecursiveComparison().isEqualTo(updatedUserResponseDto);
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userMapper, times(1)).userToUserResponseDto(any(User.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testPatchUserNotFound() {
        UserPatchDto userPatchDto = UserPatchDto.builder()
                .firstName("Jane")
                .build();

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Optional<UserResponseDto> returnedUser = userService.patchUser(1L, userPatchDto);

        assertThat(returnedUser).isEmpty();
        verify(userRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponseDto(any(User.class))).thenReturn(userResponseDto);

        UserResponseDto returnedUser = userService.deleteUser(1L).get();

        assertThat(returnedUser).usingRecursiveComparison().isEqualTo(userResponseDto);
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Optional<UserResponseDto> returnedUser = userService.deleteUser(1L);

        assertThat(returnedUser).isEmpty();
        verify(userRepository, times(1)).findById(any(Long.class));
    }
}
