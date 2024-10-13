package com.asianpaints.apse.service_engineer;

import com.asianpaints.apse.service_engineer.domain.entity.UserDesignation;
import com.asianpaints.apse.service_engineer.domain.entity.UserType;
import com.asianpaints.apse.service_engineer.domain.entity.Zone;
import com.asianpaints.apse.service_engineer.repository.UserDesignationRepository;
import com.asianpaints.apse.service_engineer.repository.UserTypeRepository;
import com.asianpaints.apse.service_engineer.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceEngineerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ServiceEngineerApplication.class, args);
    }

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private UserDesignationRepository userDesignationRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        Zone zone1 = new Zone();
        zone1.setName("East");
        Zone zone2 = new Zone();
        zone2.setName("West");
        Zone zone3 = new Zone();
        zone3.setName("North");
        Zone zone4 = new Zone();
        zone4.setName("South");
        zoneRepository.save(zone1);
        zoneRepository.save(zone2);
        zoneRepository.save(zone3);
        zoneRepository.save(zone4);

        UserDesignation userDesignation1 = new UserDesignation();
        userDesignation1.setDesignation("Service Engineer");

        UserDesignation userDesignation2 = new UserDesignation();
        userDesignation2.setDesignation("Approver");

        UserDesignation userDesignation3 = new UserDesignation();
        userDesignation3.setDesignation("Administrator");

        userDesignationRepository.save(userDesignation1);
        userDesignationRepository.save(userDesignation2);
        userDesignationRepository.save(userDesignation3);

        UserType userType1 = new UserType();
        userType1.setUserType("Service Engineer");

        UserType userType2 = new UserType();
        userType2.setUserType("Approver");

        UserType userType3 = new UserType();
        userType3.setUserType("Administrator");

        userTypeRepository.save(userType1);
        userTypeRepository.save(userType2);
        userTypeRepository.save(userType3);

    }
}
