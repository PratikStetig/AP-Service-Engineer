package com.asianpaints.apse.service_engineer.validator;

import com.asianpaints.apse.service_engineer.dto.AuthenticationRequest;
import org.springframework.stereotype.Component;


import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthenticationRequestValidator {

    private final Validator validator;

    public AuthenticationRequestValidator(Validator validator){
        this.validator = validator;
    }

    public List<String> validate(AuthenticationRequest authenticationRequest) {
        Set<ConstraintViolation<AuthenticationRequest>> violations = validator.validate(authenticationRequest);
        if(!violations.isEmpty()){
            return Collections.singletonList(getError(violations));
        }
        return Collections.emptyList();
    }

    private String getError(Set<ConstraintViolation<AuthenticationRequest>> violations){
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
    }
}
