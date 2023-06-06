package com.wvaviator.greenkeep.equipment;

import com.wvaviator.greenkeep.maintenance.Maintenance;
import com.wvaviator.greenkeep.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

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
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Equipment type is required")
    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    private LocalDateTime purchasedAt = LocalDateTime.now();

    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(min = 2, max = 50, message = "Model must be between 2 and 50 characters")
    private String model;

    @Size(max = 50, message = "Serial number must be less than 50 characters")
    private String serialNumber;

    @Size(max = 255, message = "Image URL must be less than 255 characters")
    @URL(message = "Image URL must be a valid URL")
    private String image = "https://via.placeholder.com/150";

    @NotNull(message = "User is required")
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private List<Maintenance> maintenances = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
