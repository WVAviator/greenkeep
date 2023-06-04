package com.wvaviator.greenkeep.treatment;

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
                .scheduled(true)
                .scheduledAt(LocalDateTime.now())
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

}
