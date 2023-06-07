package com.wvaviator.greenkeep.user;

public class TestUserBuilder {

    public User build() {
        return User.builder()
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .email("test@email.com")
                .build();
    }
}
