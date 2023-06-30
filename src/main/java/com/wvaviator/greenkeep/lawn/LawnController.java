package com.wvaviator.greenkeep.lawn;

import com.wvaviator.greenkeep.exceptions.NotFoundException;
import com.wvaviator.greenkeep.security.AuthenticatedUser;
import com.wvaviator.greenkeep.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LawnController {
    public static final String LAWN_PATH = "/api/v1/lawns";
    public static final String LAWN_PATH_ID = "/api/v1/lawns/{id}";

    private final LawnService lawnService;

    @GetMapping(LAWN_PATH)
    public ResponseEntity<List<LawnResponseDto>> listLawns(@AuthenticatedUser User user) {
        List<LawnResponseDto> lawnResponseDtos = lawnService.listLawnsForUser(user);

        return ResponseEntity.ok(lawnResponseDtos);
    }

    @GetMapping(LAWN_PATH_ID)
    public ResponseEntity<LawnResponseDto> getLawn(@PathVariable Long id, @AuthenticatedUser User user) {
        LawnResponseDto lawnResponseDto = lawnService.getLawn(id, user)
                .orElseThrow(() -> new NotFoundException("Lawn not found"));

        return ResponseEntity.ok(lawnResponseDto);
    }

    @PostMapping(LAWN_PATH)
    public ResponseEntity<LawnResponseDto> createLawn(@RequestBody LawnCreateDto lawnCreateDto,
                                                      @AuthenticatedUser User user) {
        LawnResponseDto lawnResponseDto = lawnService.createLawn(lawnCreateDto, user);
        return ResponseEntity.created(URI.create(LAWN_PATH + "/" + lawnResponseDto.getId()))
                .body(lawnResponseDto);
    }

    @PutMapping(LAWN_PATH_ID)
    public ResponseEntity<LawnResponseDto> putLawn(@PathVariable Long id,
                                                   @RequestBody LawnCreateDto lawnCreateDto,
                                                   @AuthenticatedUser User user) {
        LawnResponseDto lawnResponseDto = lawnService.putLawn(id, lawnCreateDto, user)
                .orElseThrow(() -> new NotFoundException("Lawn not found"));

        return ResponseEntity.ok(lawnResponseDto);
    }

    @PatchMapping(LAWN_PATH_ID)
    public ResponseEntity<LawnResponseDto> patchLawn(@PathVariable Long id,
                                                     @RequestBody LawnPatchDto lawnPatchDto,
                                                     @AuthenticatedUser User user) {
        LawnResponseDto lawnResponseDto = lawnService.patchLawn(id, lawnPatchDto, user)
                .orElseThrow(() -> new NotFoundException("Lawn not found"));

        return ResponseEntity.ok(lawnResponseDto);
    }

    @DeleteMapping(LAWN_PATH_ID)
    public ResponseEntity<Void> deleteLawn(@PathVariable Long id, @AuthenticatedUser User user) {
        lawnService.deleteLawn(id, user);
        return ResponseEntity.noContent().build();
    }


}
