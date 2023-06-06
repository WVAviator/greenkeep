package com.wvaviator.greenkeep.equipment;

import com.wvaviator.greenkeep.user.TestUserBuilder;
import com.wvaviator.greenkeep.user.User;
import com.wvaviator.greenkeep.user.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                .type(EquipmentType.MOWER)
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

    @Test
    void testSaveEquipmentWithoutUserFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            Equipment testEquipment = Equipment.builder()
                    .brand("Ryobi")
                    .model("Ryobi 40V 20\" Lawn Mower")
                    .type(EquipmentType.MOWER)
                    .serialNumber("1234567890")
                    .build();

            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }

    @Test
    void testSaveEquipmentWithoutBrandFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = new TestUserBuilder().build();
            Equipment testEquipment = Equipment.builder()
                    .model("Ryobi 40V 20\" Lawn Mower")
                    .type(EquipmentType.MOWER)
                    .serialNumber("1234567890")
                    .user(testUser)
                    .build();

            userRepository.save(testUser);
            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }

    @Test
    void testSaveEquipmentWithoutModelFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = new TestUserBuilder().build();
            Equipment testEquipment = Equipment.builder()
                    .brand("Ryobi")
                    .type(EquipmentType.MOWER)
                    .serialNumber("1234567890")
                    .user(testUser)
                    .build();

            userRepository.save(testUser);
            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }

    @Test
    void testSaveEquipmentWithoutTypeFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = new TestUserBuilder().build();
            Equipment testEquipment = Equipment.builder()
                    .brand("Ryobi")
                    .model("Ryobi 40V 20\" Lawn Mower")
                    .serialNumber("1234567890")
                    .user(testUser)
                    .build();

            userRepository.save(testUser);
            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }

    @Test
    void testSaveEquipmentSerialNumberNotRequired() {
        User testUser = new TestUserBuilder().build();
        Equipment testEquipment = Equipment.builder()
                .brand("Ryobi")
                .model("Ryobi 40V 20\" Lawn Mower")
                .type(EquipmentType.MOWER)
                .user(testUser)
                .build();

        userRepository.save(testUser);
        Equipment savedEquipment = equipmentRepository.save(testEquipment);

        equipmentRepository.flush();

        assertNotNull(savedEquipment);
        assertNotNull(savedEquipment.getId());
        assertNotNull(savedEquipment.getCreatedAt());
        assertNotNull(savedEquipment.getUpdatedAt());
    }

    @Test
    void testSaveEquipmentBrandTooLongFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = new TestUserBuilder().build();
            Equipment testEquipment = Equipment.builder()
                    .brand("A".repeat(51))
                    .model("Ryobi 40V 20\" Lawn Mower")
                    .type(EquipmentType.MOWER)
                    .serialNumber("1234567890")
                    .user(testUser)
                    .build();

            userRepository.save(testUser);
            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }

    @Test
    void testSaveEquipmentModelTooLongFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = new TestUserBuilder().build();
            Equipment testEquipment = Equipment.builder()
                    .brand("Ryobi")
                    .model("A".repeat(51))
                    .type(EquipmentType.MOWER)
                    .serialNumber("1234567890")
                    .user(testUser)
                    .build();

            userRepository.save(testUser);
            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }

    @Test
    void testSaveEquipmentSerialNumberTooLongFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = new TestUserBuilder().build();
            Equipment testEquipment = Equipment.builder()
                    .brand("Ryobi")
                    .model("Ryobi 40V 20\" Lawn Mower")
                    .type(EquipmentType.MOWER)
                    .serialNumber("A".repeat(51))
                    .user(testUser)
                    .build();

            userRepository.save(testUser);
            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }

    @Test
    void testSaveEquipmentBrandTooShortFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = new TestUserBuilder().build();
            Equipment testEquipment = Equipment.builder()
                    .brand("")
                    .model("Ryobi 40V 20\" Lawn Mower")
                    .type(EquipmentType.MOWER)
                    .serialNumber("1234567890")
                    .user(testUser)
                    .build();

            userRepository.save(testUser);
            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }

    @Test
    void testSaveEquipmentModelTooShortFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = new TestUserBuilder().build();
            Equipment testEquipment = Equipment.builder()
                    .brand("Ryobi")
                    .model("")
                    .type(EquipmentType.MOWER)
                    .serialNumber("1234567890")
                    .user(testUser)
                    .build();

            userRepository.save(testUser);
            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }

    @Test
    void testSaveEquipmentImageUrlTooLongFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = new TestUserBuilder().build();
            Equipment testEquipment = Equipment.builder()
                    .image("http://www.example.com/" + "A".repeat(256))
                    .brand("Ryobi")
                    .model("Ryobi 40V 20\" Lawn Mower")
                    .type(EquipmentType.MOWER)
                    .serialNumber("1234567890")
                    .user(testUser)
                    .build();

            userRepository.save(testUser);
            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }

    @Test
    void testSaveEquipmentImageUrlInvalidUrlFails() {
        assertThrows(ConstraintViolationException.class, () -> {
            User testUser = new TestUserBuilder().build();
            Equipment testEquipment = Equipment.builder()
                    .image("invalid url")
                    .brand("Ryobi")
                    .model("Ryobi 40V 20\" Lawn Mower")
                    .type(EquipmentType.MOWER)
                    .serialNumber("1234567890")
                    .user(testUser)
                    .build();

            userRepository.save(testUser);
            equipmentRepository.save(testEquipment);

            equipmentRepository.flush();
        });
    }
}
