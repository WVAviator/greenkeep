package com.wvaviator.greenkeep.lawn;

import com.wvaviator.greenkeep.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LawnServiceImpl implements LawnService {
    private final LawnRepository lawnRepository;


    @Override
    public LawnResponseDto createLawn(LawnCreateDto lawnCreateDto, User user) {
        return null;
    }

    @Override
    public Optional<LawnResponseDto> getLawn(Long id, User user) {
        return Optional.empty();
    }

    @Override
    public List<LawnResponseDto> listLawnsForUser(User user) {
        return null;
    }

    @Override
    public Optional<LawnResponseDto> putLawn(Long id, LawnCreateDto lawnCreateDto, User user) {
        return Optional.empty();
    }

    @Override
    public Optional<LawnResponseDto> patchLawn(Long id, LawnPatchDto lawnPatchDto, User user) {
        return Optional.empty();
    }

    @Override
    public Optional<LawnResponseDto> deleteLawn(Long id, User user) {
        return Optional.empty();
    }
}
