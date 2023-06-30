package com.wvaviator.greenkeep.lawn;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LawnCreateDto {

    @NotBlank(message = "Lawn name is required")
    @Size(min = 2, max = 50, message = "Lawn name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Zip code is required")
    @Pattern(regexp = "\\d{5}", message = "Invalid zipcode")
    private String zipCode;

    @NotNull(message = "Grass type is required")
    @Enumerated(EnumType.STRING)
    private GrassType grassType;

    @Positive(message = "Lawn size must be greater than 0")
    private Integer size;

    private Boolean hasSprinklerSystem = false;

    private String description;

    private String problems;
}
