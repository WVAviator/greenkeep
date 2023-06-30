package com.wvaviator.greenkeep.lawn;

import com.wvaviator.greenkeep.user.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LawnResponseDto {
    private Long id;
    private String name;
    private String zipCode;
    private USDAHardinessZone hardinessZone;
    private GrassType grassType;
    private Integer size;
    private Boolean hasSprinklerSystem = false;
    private String description;
    private String problems;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
