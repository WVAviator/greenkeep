package com.wvaviator.greenkeep.treatment;

import com.wvaviator.greenkeep.lawn.Lawn;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Treatment type is required")
    @Enumerated(EnumType.STRING)
    private TreatmentType type;

    @NotNull(message = "Spread type is required")
    @Enumerated(EnumType.STRING)
    private SpreadType spreadType;

    @Size(min = 2, max = 50, message = "Product must be between 2 and 50 characters")
    private String product = "None";

    @Range(min = 0, max = 1, message = "Coverage must be between 0 and 1")
    private double coverage = 1.0;

    private Boolean scheduled = false;
    private LocalDateTime scheduledAt;
    private Boolean completed = false;
    private LocalDateTime completedAt;

    @NotNull
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "lawn_id")
    private Lawn lawn;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
