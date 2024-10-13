package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.UserDesignation;
import com.asianpaints.apse.service_engineer.domain.entity.UserType;
import com.asianpaints.apse.service_engineer.domain.entity.Zone;
import com.asianpaints.apse.service_engineer.dto.AddUserRequest;
import com.asianpaints.apse.service_engineer.dto.EditUserRequest;
import com.asianpaints.apse.service_engineer.dto.SignUpRequest;

public interface ApUserMapper {
    ApUser toEntity(SignUpRequest signUpRequest,
                    UserType userType,
                    UserDesignation userDesignation,
                    Zone zone);
    ApUser toEntity(AddUserRequest addUserRequest,
                    UserType userType,
                    UserDesignation userDesignation,
                    Zone zone);

}
