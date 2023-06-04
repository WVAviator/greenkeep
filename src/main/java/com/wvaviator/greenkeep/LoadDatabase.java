package com.wvaviator.greenkeep;

import com.wvaviator.greenkeep.equipment.Equipment;
import com.wvaviator.greenkeep.equipment.EquipmentRepository;
import com.wvaviator.greenkeep.equipment.EquipmentType;
import com.wvaviator.greenkeep.lawn.Lawn;
import com.wvaviator.greenkeep.lawn.LawnRepository;
import com.wvaviator.greenkeep.maintenance.MaintenanceRepository;
import com.wvaviator.greenkeep.treatment.TreatmentRepository;
import com.wvaviator.greenkeep.user.User;
import com.wvaviator.greenkeep.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class LoadDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(LawnRepository lawnRepository, EquipmentRepository equipmentRepository,
                                   MaintenanceRepository maintenanceRepository, TreatmentRepository treatmentRepository,
                                   UserRepository userRepository) {
        return args -> {
            logger.info("Initializing database with default users, lawns, and equipment.");

            User user1 = User.builder().id(1L).firstName("John").lastName("Doe").email("john@doe.com").build();
            userRepository.save(user1);

            logger.info("Initial user created.");

            Equipment equipment1 = Equipment.builder().id(1L).brand("Ryobi").model("40V").type(EquipmentType.MOWER)
                    .user(user1).build();
            Equipment equipment2 = Equipment.builder().id(2L).brand("Ryobi").model("40V").type(EquipmentType.TRIMMER)
                    .user(user1).build();
            Equipment equipment3 = Equipment.builder().id(3L).brand("Ryobi").model("40V").type(EquipmentType.BLOWER)
                    .user(user1).build();
            equipmentRepository.save(equipment1);
            equipmentRepository.save(equipment2);
            equipmentRepository.save(equipment3);

            logger.info("Initial equipment created.");

            Lawn lawn1 = Lawn.builder().id(1L).city("Hernando").state("MS").name("Front Yard")
                    .description("The front yard of my house.").problems("There is poa annua and nutsedge.").size(2000)
                    .hasSprinklerSystem(false).user(user1).build();
            Lawn lawn2 = Lawn.builder().id(2L).city("Hernando").state("MS").name("Back Yard")
                    .description("The back yard of my house.").problems("There is crabgrass and patches of dirt.")
                    .size(2000)
                    .hasSprinklerSystem(false).user(user1).build();
            lawnRepository.save(lawn1);
            lawnRepository.save(lawn2);

            logger.info("Initial lawns created.");


            String sb = "Database initialized with the following counts:\n" +
                    "Users: " + userRepository.count() + "\n" +
                    "Lawns: " + lawnRepository.count() + "\n" +
                    "Equipment: " + equipmentRepository.count() + "\n" +
                    "Maintenances: " + maintenanceRepository.count() + "\n" +
                    "Treatments: " + treatmentRepository.count() + "\n";
            logger.info(sb);
        };
    }
}
