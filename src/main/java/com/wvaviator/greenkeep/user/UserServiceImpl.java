package com.wvaviator.greenkeep.user;

import com.wvaviator.greenkeep.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserPatchDto userPatchDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userPatchDto.getFirstName() != null) {
            user.setFirstName(userPatchDto.getFirstName());
        }

        if (userPatchDto.getLastName() != null) {
            user.setLastName(userPatchDto.getLastName());
        }

        if (userPatchDto.getEmail() != null) {
            user.setEmail(userPatchDto.getEmail());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.userToUserResponseDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
