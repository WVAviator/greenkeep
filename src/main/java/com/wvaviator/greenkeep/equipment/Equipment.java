package com.wvaviator.greenkeep.equipment;

import com.wvaviator.greenkeep.maintenance.Maintenance;
import com.wvaviator.greenkeep.user.User;
import jakarta.persistence.*;
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
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    private LocalDateTime purchasedAt = LocalDateTime.now();

    private String brand = "None";

    private String model = "None";

    private String serialNumber = "0000";

    private String image = "https://via.placeholder.com/150";

    @NonNull
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
