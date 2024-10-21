package com.asianpaints.apse.service_engineer;

import com.asianpaints.apse.service_engineer.domain.entity.UserDesignation;
import com.asianpaints.apse.service_engineer.domain.entity.UserType;
import com.asianpaints.apse.service_engineer.domain.entity.Zone;
import com.asianpaints.apse.service_engineer.dto.ApUserDto;
import com.asianpaints.apse.service_engineer.repository.UserDesignationRepository;
import com.asianpaints.apse.service_engineer.repository.UserTypeRepository;
import com.asianpaints.apse.service_engineer.repository.ZoneRepository;
import com.asianpaints.apse.service_engineer.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private ApUserService apUserService;

    @Override
    public void run(String... args) throws Exception {
//        Zone zone1 = new Zone();
//        zone1.setName("East");
//        Zone zone2 = new Zone();
//        zone2.setName("West");
//        Zone zone3 = new Zone();
//        zone3.setName("North");
//        Zone zone4 = new Zone();
//        zone4.setName("South");
//        zoneRepository.save(zone1);
//        zoneRepository.save(zone2);
//        zoneRepository.save(zone3);
//        zoneRepository.save(zone4);
//
//        UserDesignation userDesignation1 = new UserDesignation();
//        userDesignation1.setDesignation("Service Engineer");
//
//        UserDesignation userDesignation2 = new UserDesignation();
//        userDesignation2.setDesignation("Approver");
//
//        UserDesignation userDesignation3 = new UserDesignation();
//        userDesignation3.setDesignation("Administrator");
//
//        userDesignationRepository.save(userDesignation1);
//        userDesignationRepository.save(userDesignation2);
//        userDesignationRepository.save(userDesignation3);
//
//        UserType userType1 = new UserType();
//        userType1.setUserType("Service Engineer");
//
//        UserType userType2 = new UserType();
//        userType2.setUserType("Approver");
//
//        UserType userType3 = new UserType();
//        userType3.setUserType("Administrator");
//
//        userTypeRepository.save(userType1);
//        userTypeRepository.save(userType2);
//        userTypeRepository.save(userType3);
//        List<UserType> userTypeList = userTypeRepository.findAll();
//        List<Zone> zonList = zoneRepository.findAll();
//        List<UserDesignation> userDesignations = userDesignationRepository.findAll();
//
//        Map<String, Long> userTypeToIdMap = userTypeList.stream().collect(Collectors.toMap(userType -> userType.getUserType(), userType -> userType.getId()));
//        Map<String, Long> zoneNameToIdMap = zonList.stream().collect(Collectors.toMap(zone -> zone.getName(), zone -> zone.getId()));
//        Map<String, Long> designationNameToIdMap = userDesignations.stream().collect(Collectors.toMap(designation -> designation.getDesignation(), designation -> designation.getId()));
//
//        ApUserDto apUserDto = ApUserDto.builder()
//                .name("Hetalben Trivedi")
//                .userTypeId(userTypeToIdMap.get("Service Engineer"))
//                .email("hetalben.trivedi@stetig.in")
//                .zoneId(zoneNameToIdMap.get("West"))
//                .designationId(designationNameToIdMap.get("Service Engineer"))
//                .build();
//        ApUserDto apUserDto1 = ApUserDto.builder()
//                .name("Hetvi Dave")
//                .userTypeId(userTypeToIdMap.get("Approver"))
//                .email("hetalbentriveditech@gmail.com")
//                .zoneId(zoneNameToIdMap.get("West"))
//                .designationId(designationNameToIdMap.get("Approver"))
//                .build();
//        ApUserDto apUserDto2 = ApUserDto.builder()
//                .name("Ved Dave")
//                .userTypeId(userTypeToIdMap.get("Administrator"))
//                .email("trivedi.hetal79@gmail.com")
//                .zoneId(zoneNameToIdMap.get("West"))
//                .designationId(designationNameToIdMap.get("Administrator"))
//                .build();
//
//        apUserService.addUser(apUserDto);
//        apUserService.addUser(apUserDto1);
//        apUserService.addUser(apUserDto2);
    }
}
