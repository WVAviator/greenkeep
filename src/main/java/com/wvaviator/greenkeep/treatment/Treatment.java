package com.wvaviator.greenkeep.treatment;

import com.wvaviator.greenkeep.lawn.Lawn;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne
    @JoinColumn(name = "lawn_id")
    private Lawn lawn;

}
