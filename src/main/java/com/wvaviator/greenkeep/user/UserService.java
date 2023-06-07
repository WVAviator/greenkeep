package com.wvaviator.greenkeep.user;

public interface UserService {
    UserResponseDto createUser(UserCreateDto userCreateDto);

    UserResponseDto getUser(String id);

    UserResponseDto updateUser(String id, UserPatchDto userPatchDto);

    void deleteUser(String id);
}
