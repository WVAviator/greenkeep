package com.wvaviator.greenkeep.lawn;

import com.wvaviator.greenkeep.maintenance.Maintenance;
import com.wvaviator.greenkeep.treatment.Treatment;
import com.wvaviator.greenkeep.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Lawn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Lawn name is required")
    @Size(min = 2, max = 50, message = "Lawn name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Zip code is required")
    @Pattern(regexp = "\\d{5}", message = "Invalid zipcode")
    private String zipCode;

    @NotNull(message = "Hardiness zone is required")
    @Enumerated(EnumType.STRING)
    private USDAHardinessZone hardinessZone;

    @NotNull(message = "Grass type is required")
    @Enumerated(EnumType.STRING)
    private GrassType grassType;

    @Positive(message = "Lawn size must be greater than 0")
    private Integer size;

    private Boolean hasSprinklerSystem = false;

    @Column(length = 1000, columnDefinition = "text")
    private String description;

    @Column(length = 1000, columnDefinition = "text")
    private String problems;

    @NotNull(message = "User is required")
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "lawn", cascade = CascadeType.ALL)
    private List<Maintenance> maintenances = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "lawn", cascade = CascadeType.ALL)
    private List<Treatment> treatments = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
