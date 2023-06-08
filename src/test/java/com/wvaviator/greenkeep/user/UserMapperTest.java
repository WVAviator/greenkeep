package com.wvaviator.greenkeep.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {UserMapperImpl.class})
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testUserCreateDtoToUser() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build();
        User user = userMapper.userCreateDtoToUser(userCreateDto);

        assertThat(user.getFirstName()).isEqualTo(userCreateDto.getFirstName());
        assertThat(user.getLastName()).isEqualTo(userCreateDto.getLastName());
        assertThat(user.getEmail()).isEqualTo(userCreateDto.getEmail());
    }

    @Test
    void testUserToUserResponseDto() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user);

        assertThat(userResponseDto.getId()).isEqualTo(user.getId());
        assertThat(userResponseDto.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userResponseDto.getLastName()).isEqualTo(user.getLastName());
        assertThat(userResponseDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userResponseDto.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(userResponseDto.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
    }

}
