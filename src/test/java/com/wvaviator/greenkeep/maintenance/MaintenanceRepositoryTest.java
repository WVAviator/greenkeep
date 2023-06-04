package com.wvaviator.greenkeep.maintenance;

import com.wvaviator.greenkeep.equipment.Equipment;
import com.wvaviator.greenkeep.equipment.EquipmentRepository;
import com.wvaviator.greenkeep.equipment.TestEquipmentBuilder;
import com.wvaviator.greenkeep.lawn.Lawn;
import com.wvaviator.greenkeep.lawn.LawnRepository;
import com.wvaviator.greenkeep.lawn.TestLawnBuilder;
import com.wvaviator.greenkeep.user.TestUserBuilder;
import com.wvaviator.greenkeep.user.User;
import com.wvaviator.greenkeep.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class MaintenanceRepositoryTest {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LawnRepository lawnRepository;

    @Test
    void testSaveMaintenance() {
        User testUser = new TestUserBuilder().build();
        Lawn testLawn = new TestLawnBuilder().build(testUser);
        Equipment testEquipment = new TestEquipmentBuilder().build(testUser);

        Maintenance testMaintenance = Maintenance.builder()
                .equipment(testEquipment)
                .lawn(testLawn)
                .type(MaintenanceType.MOWING)
                .scheduled(true)
                .scheduledAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(testUser);
        Lawn savedLawn = lawnRepository.save(testLawn);
        Equipment savedEquipment = equipmentRepository.save(testEquipment);
        Maintenance savedMaintenance = maintenanceRepository.save(testMaintenance);

        maintenanceRepository.flush();

        assertNotNull(savedMaintenance);
        assertNotNull(savedMaintenance.getId());
        assertNotNull(savedMaintenance.getCreatedAt());
        assertNotNull(savedMaintenance.getUpdatedAt());
    }

}
