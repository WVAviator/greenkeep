package com.wvaviator.greenkeep.lawn;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LawnCreateDto {

    @NotBlank(message = "Lawn name is required")
    @Size(min = 2, max = 50, message = "Lawn name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;

    @NotNull(message = "State is required")
    @Enumerated(EnumType.STRING)
    private State state;

    @NotNull(message = "Grass type is required")
    @Enumerated(EnumType.STRING)
    private GrassType grassType;

    @Positive(message = "Lawn size must be greater than 0")
    private Integer size;

    private Boolean hasSprinklerSystem = false;

    private String description;

    private String problems;
}
