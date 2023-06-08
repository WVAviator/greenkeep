package com.wvaviator.greenkeep.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDto createUser(UserCreateDto userCreateDto);

    Optional<UserResponseDto> getUser(Long id);

    List<UserResponseDto> listUsers();

    Optional<UserResponseDto> putUser(Long id, UserCreateDto userCreateDto);

    Optional<UserResponseDto> patchUser(Long id, UserPatchDto userPatchDto);

    Optional<UserResponseDto> deleteUser(Long id);
}
