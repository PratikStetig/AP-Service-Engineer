package com.asianpaints.apse.service_engineer.validator;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.UserDesignation;
import com.asianpaints.apse.service_engineer.domain.entity.UserType;
import com.asianpaints.apse.service_engineer.domain.entity.Zone;
import com.asianpaints.apse.service_engineer.dto.ApUserDto;
import com.asianpaints.apse.service_engineer.repository.UserDesignationRepository;
import com.asianpaints.apse.service_engineer.repository.UserTypeRepository;
import com.asianpaints.apse.service_engineer.repository.ZoneRepository;
import com.asianpaints.apse.service_engineer.service.ApUserService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddUserRequestValidator {

    private final Validator validator;
    private final UserDesignationRepository userDesignationRepository;
    private final ZoneRepository zoneRepository;
    private final ApUserService apUserService;
    private final UserTypeRepository userTypeRepository;
    public AddUserRequestValidator(Validator validator,
                                   ZoneRepository zoneRepository,
                                   ApUserService apUserService,
                                   UserDesignationRepository userDesignationRepository,
                                   UserTypeRepository userTypeRepository){
        this.validator = validator;
        this.zoneRepository = zoneRepository;
        this.apUserService = apUserService;
        this.userDesignationRepository = userDesignationRepository;
        this.userTypeRepository = userTypeRepository;
    }

    public List<String> validate(ApUserDto addUserRequest) {
        Set<ConstraintViolation<ApUserDto>> violations = validator.validate(addUserRequest);
        if(!violations.isEmpty()){
            return Collections.singletonList(getError(violations));
        }
        UserDesignation userDesignation = userDesignationRepository.findById(addUserRequest.getDesignationId()).orElse(null);
        Zone zone = zoneRepository.findById(addUserRequest.getZoneId()).orElse(null);
        ApUser apUser = apUserService.findUserByEmail(addUserRequest.getEmail());
        UserType userType = userTypeRepository.findById(addUserRequest.getUserTypeId()).orElse(null);
        List<String> errors = new ArrayList<>();
        if(apUser != null){
            String errorMsg = String.format("Email %s already exist in the system",addUserRequest.getEmail());
            errors.add(errorMsg);
        }
        if(userDesignation == null)
        {
            String errorMsg = String.format("UserDesignation with given id:%s doesn't exist in the system",addUserRequest.getDesignationId());
            errors.add(errorMsg);
        }
        if(zone == null){
            String errorMsg = String.format("Zone with given id:%s doesn't exist in the system",addUserRequest.getZoneId());
            errors.add(errorMsg);
        }
        if(userType == null){
            String errorMsg = String.format("UserType with given id:%s doesn't exist in the system",addUserRequest.getUserTypeId());
            errors.add(errorMsg);
        }
        return errors;

    }

    private String getError(Set<ConstraintViolation<ApUserDto>> violations){
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

    }

}
