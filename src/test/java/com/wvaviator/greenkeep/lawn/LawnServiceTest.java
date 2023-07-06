package com.wvaviator.greenkeep.lawn;

import com.wvaviator.greenkeep.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LawnServiceTest {

    @InjectMocks
    private LawnServiceImpl lawnService;

    @Mock
    private LawnRepository lawnRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private LawnMapper lawnMapper;

    User user = User.builder()
            .id(1L)
            .firstName("John")
            .lastName("Doe")
            .email("john@doe.com")
            .build();

    LawnCreateDto lawnCreateDto = LawnCreateDto.builder()
            .name("Front yard")
            .size(2000)
            .description("The front yard of my house.")
            .grassType(GrassType.BERMUDAGRASS)
            .hasSprinklerSystem(false)
            .zipCode("38632")
            .build();

    Lawn lawn = Lawn.builder()
            .id(1L)
            .name("Front yard")
            .user(user)
            .size(2000)
            .description("The front yard of my house.")
            .grassType(GrassType.BERMUDAGRASS)
            .hasSprinklerSystem(false)
            .zipCode("38632")
            .hardinessZone(USDAHardinessZone.ZONE_7A)
            .build();

    LawnResponseDto lawnResponseDto = LawnResponseDto.builder()
            .id(1L)
            .name("Front yard")
            .user(user)
            .size(2000)
            .description("The front yard of my house.")
            .grassType(GrassType.BERMUDAGRASS)
            .hasSprinklerSystem(false)
            .zipCode("38632")
            .hardinessZone(USDAHardinessZone.ZONE_7A)
            .build();

    USDAHardinessZoneDto usdaHardinessZoneDto = new USDAHardinessZoneDto("7a");

    @Test
    void testCreateLawn() {
        when(lawnMapper.lawnCreateDtoToLawn(any(LawnCreateDto.class))).thenReturn(lawn);
        when(restTemplate.getForObject(any(String.class), any())).thenReturn(
                usdaHardinessZoneDto);
        when(lawnRepository.save(any(Lawn.class))).thenReturn(lawn);
        when(lawnMapper.lawnToLawnResponseDto(any(Lawn.class))).thenReturn(lawnResponseDto);

        LawnResponseDto returnedLawn = lawnService.createLawn(lawnCreateDto, user);

        assertThat(returnedLawn).usingRecursiveComparison().isEqualTo(lawnResponseDto);
        verify(lawnMapper, times(1)).lawnCreateDtoToLawn(any(LawnCreateDto.class));
        verify(lawnRepository, times(1)).save(any(Lawn.class));
        verify(lawnMapper, times(1)).lawnToLawnResponseDto(any(Lawn.class));
        verify(restTemplate, times(1)).getForObject(any(String.class), eq(USDAHardinessZoneDto.class));
    }

    @Test
    void testGetLawnExists() {
        when(lawnRepository.findById(any(Long.class))).thenReturn(Optional.of(lawn));
        when(lawnMapper.lawnToLawnResponseDto(any(Lawn.class))).thenReturn(lawnResponseDto);

        Optional<LawnResponseDto> lawnResponse = lawnService.getLawn(1L, user);

        assertThat(lawnResponse).isPresent();
        assertThat(lawnResponse.get()).usingRecursiveComparison().isEqualTo(lawnResponseDto);

        verify(lawnRepository, times(1)).findById(1L);
        verify(lawnMapper, times(1)).lawnToLawnResponseDto(any(Lawn.class));
    }

    @Test
    void testGetLawnWrongUser() {
        when(lawnRepository.findById(any(Long.class))).thenReturn(Optional.of(lawn));

        Optional<LawnResponseDto> lawnResponse = lawnService.getLawn(1L, User.builder().id(2L).build());

        assertThat(lawnResponse).isEmpty();
        verify(lawnRepository, times(1)).findById(1L);
    }

    @Test
    void testListLawns() {
        when(lawnRepository.findAllByUserId(any(Long.class))).thenReturn(List.of(lawn));
        when(lawnMapper.lawnsToLawnResponseDtos(anyList())).thenReturn(List.of(lawnResponseDto));

        List<LawnResponseDto> lawnsResponse = lawnService.listLawnsForUser(user);

        assertThat(lawnsResponse).asList().containsExactly(lawnResponseDto);

        verify(lawnRepository, times(1)).findAllByUserId(1L);
        verify(lawnMapper, times(1)).lawnsToLawnResponseDtos(anyList());
    }
}
