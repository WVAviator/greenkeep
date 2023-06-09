package com.wvaviator.greenkeep.user;

import com.wvaviator.greenkeep.exceptions.NotFoundException;
import com.wvaviator.greenkeep.security.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String USER_PATH = "/api/v1/users";
    public static final String USER_PATH_ID = "/api/v1/users/{id}";

    private final UserService userService;

    @GetMapping(USER_PATH + "/me")
    public ResponseEntity<UserResponseDto> test(@AuthenticatedUser User user) {
        UserResponseDto userResponseDto = userService.getUser(user.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping(USER_PATH)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> listUsers() {
        List<UserResponseDto> userResponseDtos = userService.listUsers();

        return ResponseEntity.ok(userResponseDtos);
    }

    @GetMapping(USER_PATH_ID)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getUser(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping(USER_PATH)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.createUser(userCreateDto);
        return ResponseEntity.created(URI.create(USER_PATH + "/" + userResponseDto.getId()))
                .body(userResponseDto);
    }

    @PutMapping(USER_PATH_ID)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> putUser(@PathVariable Long id,
                                                   @RequestBody @Valid UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.putUser(id, userCreateDto)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping(USER_PATH_ID)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> patchUser(@PathVariable Long id,
                                                     @RequestBody @Valid UserPatchDto userPatchDto) {
        UserResponseDto userResponseDto = userService.patchUser(id, userPatchDto)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping(USER_PATH_ID)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.deleteUser(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return ResponseEntity.ok(userResponseDto);
    }
}
