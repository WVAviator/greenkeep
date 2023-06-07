package com.wvaviator.greenkeep.lawn;

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
class LawnRepositoryTest {

    @Autowired
    private LawnRepository lawnRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveLawn() {
        User testUser = new TestUserBuilder().build();
        Lawn testLawn = Lawn.builder()
                .name("My Lawn")
                .city("My City")
                .state(State.MS)
                .size(1000)
                .user(testUser)
                .grassType(GrassType.BERMUDAGRASS)
                .hardinessZone(USDAHardinessZone.ZONE_7B)
                .build();

        userRepository.save(testUser);
        Lawn savedLawn = lawnRepository.save(testLawn);

        lawnRepository.flush();

        assertNotNull(savedLawn);
        assertNotNull(savedLawn.getId());
        assertNotNull(savedLawn.getCreatedAt());
        assertNotNull(savedLawn.getUpdatedAt());
    }

    @Test
    void testSaveLawnNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = Lawn.builder()
                    .name("A".repeat(51))
                    .city("My City")
                    .state(State.MS)
                    .size(1000)
                    .user(testUser)
                    .grassType(GrassType.BERMUDAGRASS)
                    .hardinessZone(USDAHardinessZone.ZONE_7B)
                    .build();

            userRepository.save(testUser);
            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

    @Test
    void testSaveLawnNameTooShort() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = Lawn.builder()
                    .name("")
                    .city("My City")
                    .state(State.MS)
                    .size(1000)
                    .user(testUser)
                    .grassType(GrassType.BERMUDAGRASS)
                    .hardinessZone(USDAHardinessZone.ZONE_7B)
                    .build();

            userRepository.save(testUser);
            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

    @Test
    void testSaveLawnNameMissing() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = Lawn.builder()
                    .city("My City")
                    .state(State.MS)
                    .size(1000)
                    .user(testUser)
                    .grassType(GrassType.BERMUDAGRASS)
                    .hardinessZone(USDAHardinessZone.ZONE_7B)
                    .build();

            userRepository.save(testUser);
            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

    @Test
    void testSaveLawnCityTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = Lawn.builder()
                    .name("My Lawn")
                    .city("A".repeat(51))
                    .state(State.MS)
                    .size(1000)
                    .user(testUser)
                    .grassType(GrassType.BERMUDAGRASS)
                    .hardinessZone(USDAHardinessZone.ZONE_7B)
                    .build();

            userRepository.save(testUser);
            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

    @Test
    void testSaveLawnCityTooShort() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = Lawn.builder()
                    .name("My Lawn")
                    .city("")
                    .state(State.MS)
                    .size(1000)
                    .user(testUser)
                    .grassType(GrassType.BERMUDAGRASS)
                    .hardinessZone(USDAHardinessZone.ZONE_7B)
                    .build();

            userRepository.save(testUser);
            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

    @Test
    void testSaveLawnCityMissing() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = Lawn.builder()
                    .name("My Lawn")
                    .state(State.MS)
                    .size(1000)
                    .user(testUser)
                    .grassType(GrassType.BERMUDAGRASS)
                    .hardinessZone(USDAHardinessZone.ZONE_7B)
                    .build();

            userRepository.save(testUser);
            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

    @Test
    void testSaveLawnStateMissing() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = Lawn.builder()
                    .name("My Lawn")
                    .city("My City")
                    .size(1000)
                    .user(testUser)
                    .grassType(GrassType.BERMUDAGRASS)
                    .hardinessZone(USDAHardinessZone.ZONE_7B)
                    .build();

            userRepository.save(testUser);
            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

    @Test
    void testSaveLawnSizeTooSmall() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = Lawn.builder()
                    .name("My Lawn")
                    .city("My City")
                    .state(State.MS)
                    .size(0)
                    .user(testUser)
                    .grassType(GrassType.BERMUDAGRASS)
                    .hardinessZone(USDAHardinessZone.ZONE_7B)
                    .build();

            userRepository.save(testUser);
            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

    @Test
    void testSaveLawnGrassTypeMissing() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = Lawn.builder()
                    .name("My Lawn")
                    .city("My City")
                    .state(State.MS)
                    .size(1000)
                    .user(testUser)
                    .hardinessZone(USDAHardinessZone.ZONE_7B)
                    .build();

            userRepository.save(testUser);
            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

    @Test
    void testSaveLawnHardinessZoneMissing() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = Lawn.builder()
                    .name("My Lawn")
                    .city("My City")
                    .state(State.MS)
                    .size(1000)
                    .user(testUser)
                    .grassType(GrassType.BERMUDAGRASS)
                    .build();

            userRepository.save(testUser);
            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

    @Test
    void testSaveLawnUserMissing() {
        assertThrows(ConstraintViolationException.class, () -> {

            Lawn testLawn = Lawn.builder()
                    .name("My Lawn")
                    .city("My City")
                    .state(State.MS)
                    .size(1000)
                    .grassType(GrassType.BERMUDAGRASS)
                    .hardinessZone(USDAHardinessZone.ZONE_7B)
                    .build();

            Lawn savedLawn = lawnRepository.save(testLawn);

            lawnRepository.flush();
        });
    }

}
