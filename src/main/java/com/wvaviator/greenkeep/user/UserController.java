package com.wvaviator.greenkeep.user;

import com.wvaviator.greenkeep.exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listUsers() {
        List<UserResponseDto> userResponseDtos = userService.listUsers();

        return ResponseEntity.ok(userResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getUser(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return ResponseEntity.ok(userService.createUser(userCreateDto));
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                      @RequestBody @Valid UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.putUser(id, userCreateDto)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                      @RequestBody @Valid UserPatchDto userPatchDto) {
        UserResponseDto userResponseDto = userService.patchUser(id, userPatchDto)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.deleteUser(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return ResponseEntity.ok(userResponseDto);
    }
}
