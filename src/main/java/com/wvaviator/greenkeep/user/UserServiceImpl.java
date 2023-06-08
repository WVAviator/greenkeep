package com.wvaviator.greenkeep.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    public List<UserResponseDto> listUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDto> getUser(Long id) {
        return Optional.ofNullable(userMapper.userToUserResponseDto(userRepository.findById(id).orElse(null)));
    }

    @Override
    public Optional<UserResponseDto> putUser(Long id, UserCreateDto userCreateDto) {
        AtomicReference<Optional<UserResponseDto>> userResponseDtoOptional =
                new AtomicReference<>(Optional.empty());

        userRepository.findById(id).ifPresentOrElse((User user) -> {
            user.setFirstName(userCreateDto.getFirstName());
            user.setLastName(userCreateDto.getLastName());
            user.setEmail(userCreateDto.getEmail());
            userRepository.save(user);
            userResponseDtoOptional.set(Optional.of(userMapper.userToUserResponseDto(user)));
        }, () -> {
            User user = userMapper.userCreateDtoToUser(userCreateDto);
            User savedUser = userRepository.save(user);
            userResponseDtoOptional.set(Optional.of(userMapper.userToUserResponseDto(savedUser)));
        });

        return userResponseDtoOptional.get();
    }

    @Override
    public Optional<UserResponseDto> patchUser(Long id, UserPatchDto userPatchDto) {

        AtomicReference<Optional<UserResponseDto>> userResponseDtoOptional =
                new AtomicReference<>(Optional.empty());

        userRepository.findById(id).ifPresent((User user) -> {
            if (userPatchDto.getFirstName() != null) {
                user.setFirstName(userPatchDto.getFirstName());
            }
            if (userPatchDto.getLastName() != null) {
                user.setLastName(userPatchDto.getLastName());
            }
            if (userPatchDto.getEmail() != null) {
                user.setEmail(userPatchDto.getEmail());
            }
            userRepository.save(user);
            userResponseDtoOptional.set(Optional.of(userMapper.userToUserResponseDto(user)));
        });

        return userResponseDtoOptional.get();
    }

    @Override
    public Optional<UserResponseDto> deleteUser(Long id) {
        AtomicReference<Optional<UserResponseDto>> userResponseDtoOptional =
                new AtomicReference<>(Optional.empty());
        userRepository.findById(id).ifPresent((User user) -> {
            userRepository.delete(user);
            userResponseDtoOptional.set(Optional.of(userMapper.userToUserResponseDto(user)));
        });

        return userResponseDtoOptional.get();
    }
}
