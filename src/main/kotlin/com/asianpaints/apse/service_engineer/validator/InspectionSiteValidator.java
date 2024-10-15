package com.asianpaints.apse.service_engineer.validator;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteRequest;
import com.asianpaints.apse.service_engineer.exception.UserNotFoundException;
import com.asianpaints.apse.service_engineer.service.ApUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InspectionSiteValidator {
    private final Validator validator;
    private final ApUserService apUserService;

    public List<String> validate(InspectionSiteRequest inspectionSiteRequest) {
        Set<ConstraintViolation<InspectionSiteRequest>> violations = validator.validate(inspectionSiteRequest);
        if(!violations.isEmpty()){
            return Collections.singletonList(getError(violations));
        }
        return Collections.emptyList();
    }
    private String getError(Set<ConstraintViolation<InspectionSiteRequest>> violations){
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
    }
}
