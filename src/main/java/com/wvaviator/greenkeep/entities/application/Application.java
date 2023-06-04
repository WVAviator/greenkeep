package com.wvaviator.greenkeep.entities.application;

import com.wvaviator.greenkeep.entities.lawn.Lawn;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApplicationType type;

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
