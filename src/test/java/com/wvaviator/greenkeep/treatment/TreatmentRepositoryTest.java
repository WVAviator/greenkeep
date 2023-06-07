package com.wvaviator.greenkeep.treatment;

import com.wvaviator.greenkeep.lawn.Lawn;
import com.wvaviator.greenkeep.lawn.LawnRepository;
import com.wvaviator.greenkeep.lawn.TestLawnBuilder;
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
class TreatmentRepositoryTest {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private LawnRepository lawnRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveTreatment() {
        User testUser = new TestUserBuilder().build();
        Lawn testLawn = new TestLawnBuilder().build(testUser);
        Treatment testTreatment = Treatment.builder()
                .lawn(testLawn)
                .type(TreatmentType.FERTILIZER)
                .spreadType(SpreadType.BROADCAST)
                .build();

        User savedUser = userRepository.save(testUser);
        Lawn savedLawn = lawnRepository.save(testLawn);
        Treatment savedTreatment = treatmentRepository.save(testTreatment);

        treatmentRepository.flush();

        assertNotNull(savedTreatment);
        assertNotNull(savedTreatment.getId());
        assertNotNull(savedTreatment.getCreatedAt());
        assertNotNull(savedTreatment.getUpdatedAt());
    }

    @Test
    void testSaveTreatmentWithoutLawn() {
        assertThrows(ConstraintViolationException.class, () -> {

            Treatment testTreatment = Treatment.builder()
                    .type(TreatmentType.FERTILIZER)
                    .spreadType(SpreadType.BROADCAST)
                    .build();

            treatmentRepository.save(testTreatment);

            treatmentRepository.flush();
        });
    }

    @Test
    void testSaveTreatmentWithoutType() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = new TestLawnBuilder().build(testUser);

            Treatment testTreatment = Treatment.builder()
                    .lawn(testLawn)
                    .spreadType(SpreadType.BROADCAST)
                    .build();

            userRepository.save(testUser);
            lawnRepository.save(testLawn);
            treatmentRepository.save(testTreatment);

            treatmentRepository.flush();
        });
    }

    @Test
    void testSaveTreatmentWithoutSpreadType() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = new TestLawnBuilder().build(testUser);

            Treatment testTreatment = Treatment.builder()
                    .lawn(testLawn)
                    .type(TreatmentType.FERTILIZER)
                    .build();

            userRepository.save(testUser);
            lawnRepository.save(testLawn);
            treatmentRepository.save(testTreatment);

            treatmentRepository.flush();
        });
    }

    @Test
    void testSaveTreatmentProductTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = new TestLawnBuilder().build(testUser);

            Treatment testTreatment = Treatment.builder()
                    .lawn(testLawn)
                    .type(TreatmentType.FERTILIZER)
                    .spreadType(SpreadType.BROADCAST)
                    .product("A".repeat(51))
                    .build();

            userRepository.save(testUser);
            lawnRepository.save(testLawn);
            treatmentRepository.save(testTreatment);

            treatmentRepository.flush();
        });
    }

    @Test
    void testSaveTreatmentProductTooShort() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = new TestLawnBuilder().build(testUser);

            Treatment testTreatment = Treatment.builder()
                    .lawn(testLawn)
                    .type(TreatmentType.FERTILIZER)
                    .spreadType(SpreadType.BROADCAST)
                    .product("A")
                    .build();

            userRepository.save(testUser);
            lawnRepository.save(testLawn);
            treatmentRepository.save(testTreatment);

            treatmentRepository.flush();
        });
    }

    @Test
    void testSaveTreatmentCoverageNegative() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = new TestLawnBuilder().build(testUser);

            Treatment testTreatment = Treatment.builder()
                    .lawn(testLawn)
                    .type(TreatmentType.FERTILIZER)
                    .spreadType(SpreadType.BROADCAST)
                    .coverage(-1)
                    .build();

            userRepository.save(testUser);
            lawnRepository.save(testLawn);
            treatmentRepository.save(testTreatment);

            treatmentRepository.flush();
        });
    }

    @Test
    void testSaveTreatmentCoverageGreaterThanOne() {
        assertThrows(ConstraintViolationException.class, () -> {

            User testUser = new TestUserBuilder().build();
            Lawn testLawn = new TestLawnBuilder().build(testUser);

            Treatment testTreatment = Treatment.builder()
                    .lawn(testLawn)
                    .type(TreatmentType.FERTILIZER)
                    .spreadType(SpreadType.BROADCAST)
                    .coverage(1.1)
                    .build();

            userRepository.save(testUser);
            lawnRepository.save(testLawn);
            treatmentRepository.save(testTreatment);

            treatmentRepository.flush();
        });
    }

}
