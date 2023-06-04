package com.wvaviator.greenkeep.treatment;

import com.wvaviator.greenkeep.lawn.Lawn;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Enumerated(EnumType.STRING)
    private TreatmentType type;

    @Enumerated(EnumType.STRING)
    private SpreadType spreadType;

    private String product = "None";

    private double coverage = 1.0;

    private Boolean scheduled = false;
    private LocalDateTime scheduledAt;
    private Boolean completed = false;
    private LocalDateTime completedAt;

    @NonNull
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "lawn_id")
    private Lawn lawn;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
