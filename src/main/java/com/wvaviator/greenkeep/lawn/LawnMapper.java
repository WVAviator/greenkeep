package com.wvaviator.greenkeep.lawn;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface LawnMapper {

    Lawn lawnCreateDtoToLawn(LawnCreateDto lawnCreateDto);

    List<LawnResponseDto> lawnsToLawnResponseDtos(List<Lawn> lawns);

    LawnResponseDto lawnToLawnResponseDto(Lawn lawn);
}
