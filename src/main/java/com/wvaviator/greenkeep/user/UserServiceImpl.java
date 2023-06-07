package com.wvaviator.greenkeep.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        return null;
    }

    @Override
    public UserResponseDto getUser(String id) {
        return null;
    }

    @Override
    public UserResponseDto updateUser(String id, UserPatchDto userPatchDto) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }
}
