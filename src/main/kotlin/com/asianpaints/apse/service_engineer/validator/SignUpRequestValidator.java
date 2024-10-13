package com.asianpaints.apse.service_engineer.validator;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.UserDesignation;
import com.asianpaints.apse.service_engineer.domain.entity.UserType;
import com.asianpaints.apse.service_engineer.domain.entity.Zone;
import com.asianpaints.apse.service_engineer.dto.SignUpRequest;
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
public class SignUpRequestValidator {

    private final Validator validator;
    private final UserDesignationRepository userDesignationRepository;
    private final ZoneRepository zoneRepository;
    private final ApUserService apUserService;

    public SignUpRequestValidator(Validator validator,
                                  ZoneRepository zoneRepository,
                                  ApUserService apUserService,
                                  UserDesignationRepository userDesignationRepository){
        this.validator = validator;
        this.zoneRepository = zoneRepository;
        this.apUserService = apUserService;
        this.userDesignationRepository = userDesignationRepository;
    }

    public List<String> validate(SignUpRequest signUpRequest) {
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        if(!violations.isEmpty()){
            return Collections.singletonList(getError(violations));
        }
        UserDesignation userDesignation = userDesignationRepository.findById(signUpRequest.getDesignationId()).orElse(null);
        Zone zone = zoneRepository.findById(signUpRequest.getZoneId()).orElse(null);
        ApUser apUser = apUserService.findUserByEmail(signUpRequest.getEmail());
        List<String> errors = validateSignUpRequest(signUpRequest,
                apUser,
                userDesignation,
                zone);
         return errors;
    }

    private String getError(Set<ConstraintViolation<SignUpRequest>> violations){
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

    }
    private List<String> validateSignUpRequest(SignUpRequest signUpRequest,
                                               ApUser apUser,
                                               UserDesignation userDesignation,
                                               Zone zone){
        List<String> errors = new ArrayList<>();
        if(apUser != null){
            String errorMsg = String.format("Email %s already exist in the system",signUpRequest.getEmail());
            errors.add(errorMsg);
        }
        if(userDesignation == null)
        {
            String errorMsg = String.format("UserDesignation with given id:%s doesn't exist in the system",signUpRequest.getDesignationId());
            errors.add(errorMsg);
        }
        if(zone == null){
            String errorMsg = String.format("Zone with given id:%s doesn't exist in the system",signUpRequest.getZoneId());
            errors.add(errorMsg);
        }
        return errors;
    }
}
