package com.wvaviator.greenkeep.user;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User userCreateDtoToUser(UserCreateDto userCreateDto);

    List<UserResponseDto> usersToUserResponseDtos(List<User> users);

    UserResponseDto userToUserResponseDto(User user);
}
