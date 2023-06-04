package com.wvaviator.greenkeep.equipment;

import com.wvaviator.greenkeep.user.User;
import com.wvaviator.greenkeep.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class EquipmentRepositoryTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveEquipment() {
        User testUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build();
        Equipment testEquipment = Equipment.builder()
                .brand("Ryobi")
                .model("Ryobi 40V 20\" Lawn Mower")
                .serialNumber("1234567890")
                .user(testUser)
                .build();

        User savedUser = userRepository.save(testUser);
        Equipment savedEquipment = equipmentRepository.save(testEquipment);

        equipmentRepository.flush();

        assertNotNull(savedEquipment);
        assertNotNull(savedEquipment.getId());
        assertNotNull(savedEquipment.getCreatedAt());
        assertNotNull(savedEquipment.getUpdatedAt());
    }
}
