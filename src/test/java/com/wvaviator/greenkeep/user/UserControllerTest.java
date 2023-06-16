package com.wvaviator.greenkeep.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void testGetUserById() throws Exception {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build();

        BDDMockito.given(userService.getUser(1L)).willReturn(Optional.of(userResponseDto));

        mockMvc.perform(get(UserController.USER_PATH_ID, userResponseDto.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@doe.com"));
    }

    @Test
    void testGetUserByIdThrowsNotFoundException() throws Exception {
        BDDMockito.given(userService.getUser(1L)).willReturn(Optional.empty());

        mockMvc.perform(get(UserController.USER_PATH_ID, 1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        BDDMockito.verify(userService).getUser(1L);
    }

    @Test
    void testGetUserList() throws Exception {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build();

        BDDMockito.given(userService.listUsers()).willReturn(List.of(userResponseDto));

        mockMvc.perform(get(UserController.USER_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john@doe.com"));

        BDDMockito.verify(userService).listUsers();
    }

    @Test
    void testCreateUser() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build();

        BDDMockito.given(userService.createUser(userCreateDto)).willReturn(UserResponseDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build());

        mockMvc.perform(post(UserController.USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@doe.com"));

        BDDMockito.verify(userService).createUser(userCreateDto);
    }

    @Test
    void testCreateUserFailsWithoutFirstName() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .lastName("Doe")
                .email("john@doe.com")
                .build();

        mockMvc.perform(post(UserController.USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).createUser(userCreateDto);
    }

    @Test
    void testCreateUserFailsWithoutLastName() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("John")
                .email("john@doe.com")
                .build();

        mockMvc.perform(post(UserController.USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).createUser(userCreateDto);
    }

    @Test
    void testCreateUserFailsWithoutEmail() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        mockMvc.perform(post(UserController.USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).createUser(userCreateDto);
    }

    @Test
    void testCreateUserFailsWithInvalidEmail() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john")
                .build();

        mockMvc.perform(post(UserController.USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).createUser(userCreateDto);
    }

    @Test
    void testPutUser() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
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

        BDDMockito.given(userService.putUser(1L, userCreateDto)).willReturn(Optional.of(userResponseDto));

        mockMvc.perform(put(UserController.USER_PATH_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@doe.com"));

        BDDMockito.verify(userService).putUser(1L, userCreateDto);
    }

    @Test
    void testPutUserFailsWithoutFirstName() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .lastName("Doe")
                .email("john@doe.com")
                .build();

        mockMvc.perform(put(UserController.USER_PATH_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).putUser(1L, userCreateDto);
    }

    @Test
    void testPutUserFailsWithoutLastName() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("John")
                .email("john@doe.com")
                .build();

        mockMvc.perform(put(UserController.USER_PATH_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).putUser(1L, userCreateDto);
    }

    @Test
    void testPutUserFailsWithoutEmail() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        mockMvc.perform(put(UserController.USER_PATH_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).putUser(1L, userCreateDto);
    }

    @Test
    void testPutUserFailsWithInvalidEmail() throws Exception {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john")
                .build();

        mockMvc.perform(put(UserController.USER_PATH_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).putUser(1L, userCreateDto);
    }

    @Test
    void testPatchUser() throws Exception {
        UserPatchDto userPatchDto = UserPatchDto.builder()
                .firstName("John")
                .build();
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build();

        BDDMockito.given(userService.patchUser(1L, userPatchDto)).willReturn(Optional.of(userResponseDto));

        mockMvc.perform(patch(UserController.USER_PATH_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPatchDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@doe.com"));

        BDDMockito.verify(userService).patchUser(1L, userPatchDto);
    }

    @Test
    void testPatchUserFailsWithInvalidEmail() throws Exception {
        UserPatchDto userPatchDto = UserPatchDto.builder()
                .email("john")
                .build();

        mockMvc.perform(patch(UserController.USER_PATH_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPatchDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).patchUser(1L, userPatchDto);
    }

    @Test
    void testPatchUserFailsWithInvalidFirstName() throws Exception {
        UserPatchDto userPatchDto = UserPatchDto.builder()
                .firstName("J")
                .build();

        mockMvc.perform(patch(UserController.USER_PATH_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPatchDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).patchUser(1L, userPatchDto);
    }

    @Test
    void testPatchUserFailsWithInvalidLastName() throws Exception {
        UserPatchDto userPatchDto = UserPatchDto.builder()
                .lastName("A".repeat(51))
                .build();

        mockMvc.perform(patch(UserController.USER_PATH_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPatchDto)))
                .andExpect(status().isBadRequest());

        BDDMockito.verify(userService, BDDMockito.never()).patchUser(1L, userPatchDto);
    }

    @Test
    void testDeleteUser() throws Exception {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build();

        BDDMockito.given(userService.deleteUser(1L)).willReturn(Optional.of(userResponseDto));

        mockMvc.perform(delete(UserController.USER_PATH_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@doe.com"));

        BDDMockito.verify(userService).deleteUser(1L);
    }

    @Test
    void testDeleteUserFailsWithNonExistingUser() throws Exception {
        BDDMockito.given(userService.deleteUser(1L)).willReturn(Optional.empty());

        mockMvc.perform(delete(UserController.USER_PATH_ID, 1L))
                .andExpect(status().isNotFound());

        BDDMockito.verify(userService).deleteUser(1L);
    }

}
