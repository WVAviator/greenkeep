package com.wvaviator.greenkeep.lawn;

import com.wvaviator.greenkeep.user.TestUserBuilder;
import com.wvaviator.greenkeep.user.User;

public class TestLawnBuilder {

    public Lawn build() {
        return Lawn.builder()
                .name("My Lawn")
                .city("My City")
                .state("My State")
                .size(1000)
                .user(new TestUserBuilder().build())
                .grassType(GrassType.BERMUDAGRASS)
                .build();
    }

    public Lawn build(User user) {
        return Lawn.builder()
                .name("My Lawn")
                .city("My City")
                .state("My State")
                .size(1000)
                .user(user)
                .grassType(GrassType.BERMUDAGRASS)
                .build();
    }
}
