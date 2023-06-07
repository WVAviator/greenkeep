package com.wvaviator.greenkeep.equipment;

import com.wvaviator.greenkeep.user.TestUserBuilder;
import com.wvaviator.greenkeep.user.User;

public class TestEquipmentBuilder {


    public Equipment build() {
        return Equipment.builder()
                .brand("Test Brand")
                .model("Test Model")
                .serialNumber("Test Serial Number")
                .type(EquipmentType.MOWER)
                .user(new TestUserBuilder().build())
                .build();
    }

    public Equipment build(User user) {
        return Equipment.builder()
                .brand("Test Brand")
                .model("Test Model")
                .serialNumber("Test Serial Number")
                .type(EquipmentType.MOWER)
                .user(user)
                .build();
    }
}
