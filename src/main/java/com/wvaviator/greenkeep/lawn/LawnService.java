package com.wvaviator.greenkeep.lawn;

import com.wvaviator.greenkeep.user.User;

import java.util.List;
import java.util.Optional;

public interface LawnService {
    LawnResponseDto createLawn(LawnCreateDto lawnCreateDto, User user);

    Optional<LawnResponseDto> getLawn(Long id, User user);

    List<LawnResponseDto> listLawnsForUser(User user);

    Optional<LawnResponseDto> putLawn(Long id, LawnCreateDto lawnCreateDto, User user);

    Optional<LawnResponseDto> patchLawn(Long id, LawnPatchDto lawnPatchDto, User user);

    Optional<LawnResponseDto> deleteLawn(Long id, User user);

}
