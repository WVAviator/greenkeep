package com.wvaviator.greenkeep.user;

public interface UserService {
    UserResponseDto createUser(UserCreateDto userCreateDto);

    UserResponseDto getUser(Long id);

    UserResponseDto updateUser(Long id, UserPatchDto userPatchDto);

    void deleteUser(Long id);
}
