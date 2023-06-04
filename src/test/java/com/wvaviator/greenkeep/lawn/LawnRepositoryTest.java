package com.wvaviator.greenkeep.lawn;

import com.wvaviator.greenkeep.user.User;
import com.wvaviator.greenkeep.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class LawnRepositoryTest {

    @Autowired
    private LawnRepository lawnRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveLawn() {
        User testUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build();
        Lawn testLawn = Lawn.builder()
                .name("My Lawn")
                .city("My City")
                .state("My State")
                .size(1000)
                .user(testUser)
                .grassType(GrassType.BERMUDAGRASS)
                .build();
        User savedUser = userRepository.save(testUser);
        Lawn savedLawn = lawnRepository.save(testLawn);

        lawnRepository.flush();

        assertNotNull(savedLawn);
        assertNotNull(savedLawn.getId());
        assertNotNull(savedLawn.getCreatedAt());
        assertNotNull(savedLawn.getUpdatedAt());
    }

}
