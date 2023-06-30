package com.wvaviator.greenkeep.lawn;

import com.wvaviator.greenkeep.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class LawnServiceImpl implements LawnService {
    private final LawnRepository lawnRepository;
    private final LawnMapper lawnMapper;
    private final RestTemplate restTemplate;

    private final String HARDINESS_QUERY_URL = "https://phzmapi.org/";

    private USDAHardinessZone getHardinessZone(String zipCode) {
        try {
            USDAHardinessZoneDto hardinessZoneDto = restTemplate.getForObject(HARDINESS_QUERY_URL + zipCode + ".json",
                    USDAHardinessZoneDto.class);
            return USDAHardinessZone.valueOf("ZONE_" + hardinessZoneDto.getZone().toUpperCase());
        } catch (Exception e) {
            return USDAHardinessZone.ZONE_UNKNOWN;
        }
    }

    @Override
    public LawnResponseDto createLawn(LawnCreateDto lawnCreateDto, User user) {
        USDAHardinessZone hardinessZone = getHardinessZone(lawnCreateDto.getZipCode());

        Lawn lawn = lawnMapper.lawnCreateDtoToLawn(lawnCreateDto);
        lawn.setHardinessZone(hardinessZone);
        lawn.setUser(user);

        Lawn savedLawn = lawnRepository.save(lawn);

        return lawnMapper.lawnToLawnResponseDto(savedLawn);
    }

    @Override
    public Optional<LawnResponseDto> getLawn(Long id, User user) {
        Optional<Lawn> lawnOptional = lawnRepository.findById(id);

        if (lawnOptional.isEmpty()) {
            return Optional.empty();
        }

        Lawn lawn = lawnOptional.get();

        if (!lawn.getUser().equals(user)) {
            return Optional.empty();
        }

        return Optional.of(lawnMapper.lawnToLawnResponseDto(lawn));
    }

    @Override
    public List<LawnResponseDto> listLawnsForUser(User user) {
        List<Lawn> lawns = lawnRepository.findAllByUserId(user.getId());

        return lawnMapper.lawnsToLawnResponseDtos(lawns);
    }

    @Override
    public Optional<LawnResponseDto> putLawn(Long id, LawnCreateDto lawnCreateDto, User user) {
        AtomicReference<Optional<LawnResponseDto>> lawnResponseDtoOptional = new AtomicReference<>(Optional.empty());

        lawnRepository.findById(id).ifPresent(lawn -> {
            if (lawn.getUser().equals(user)) {

                lawn.setName(lawnCreateDto.getName());

                if (!lawn.getZipCode().equals(lawnCreateDto.getZipCode())) {
                    lawn.setZipCode(lawnCreateDto.getZipCode());
                    lawn.setHardinessZone(getHardinessZone(lawnCreateDto.getZipCode()));
                }

                lawn.setGrassType(lawnCreateDto.getGrassType());
                lawn.setSize(lawnCreateDto.getSize());
                lawn.setHasSprinklerSystem(lawnCreateDto.getHasSprinklerSystem());
                lawn.setDescription(lawnCreateDto.getDescription());
                lawn.setProblems(lawnCreateDto.getProblems());

                Lawn savedLawn = lawnRepository.save(lawn);

                lawnResponseDtoOptional.set(Optional.of(lawnMapper.lawnToLawnResponseDto(savedLawn)));
            }
        });

        return lawnResponseDtoOptional.get();
    }

    @Override
    public Optional<LawnResponseDto> patchLawn(Long id, LawnPatchDto lawnPatchDto, User user) {
        AtomicReference<Optional<LawnResponseDto>> lawnResponseDtoOptional = new AtomicReference<>(Optional.empty());

        lawnRepository.findById(id).ifPresent(lawn -> {
            if (lawn.getUser().equals(user)) {
                if (lawnPatchDto.getName() != null) {
                    lawn.setName(lawnPatchDto.getName());
                }

                if (lawnPatchDto.getZipCode() != null) {
                    lawn.setZipCode(lawnPatchDto.getZipCode());
                    lawn.setHardinessZone(getHardinessZone(lawnPatchDto.getZipCode()));
                }

                if (lawnPatchDto.getGrassType() != null) {
                    lawn.setGrassType(lawnPatchDto.getGrassType());
                }

                if (lawnPatchDto.getSize() != null) {
                    lawn.setSize(lawnPatchDto.getSize());
                }

                if (lawnPatchDto.getHasSprinklerSystem() != null) {
                    lawn.setHasSprinklerSystem(lawnPatchDto.getHasSprinklerSystem());
                }

                if (lawnPatchDto.getDescription() != null) {
                    lawn.setDescription(lawnPatchDto.getDescription());
                }

                if (lawnPatchDto.getProblems() != null) {
                    lawn.setProblems(lawnPatchDto.getProblems());
                }

                Lawn savedLawn = lawnRepository.save(lawn);

                lawnResponseDtoOptional.set(Optional.of(lawnMapper.lawnToLawnResponseDto(savedLawn)));
            }
        });

        return lawnResponseDtoOptional.get();
    }

    @Override
    public Optional<LawnResponseDto> deleteLawn(Long id, User user) {
        AtomicReference<Optional<LawnResponseDto>> lawnResponseDtoOptional = new AtomicReference<>(Optional.empty());

        lawnRepository.findById(id).ifPresent(lawn -> {
            if (lawn.getUser().equals(user)) {
                lawnRepository.delete(lawn);

                lawnResponseDtoOptional.set(Optional.of(lawnMapper.lawnToLawnResponseDto(lawn)));
            }
        });

        return lawnResponseDtoOptional.get();
    }
}
