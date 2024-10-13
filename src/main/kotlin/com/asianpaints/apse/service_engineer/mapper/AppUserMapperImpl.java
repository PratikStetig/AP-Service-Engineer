package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.UserDesignation;
import com.asianpaints.apse.service_engineer.domain.entity.UserType;
import com.asianpaints.apse.service_engineer.domain.entity.Zone;
import com.asianpaints.apse.service_engineer.dto.AddUserRequest;
import com.asianpaints.apse.service_engineer.dto.SignUpRequest;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapperImpl implements ApUserMapper {

    @Override
    public ApUser toEntity(SignUpRequest signUpRequest,
                           UserType userType,
                           UserDesignation userDesignation,
                           Zone zone) {
       if(signUpRequest == null){
           return null;
       }
       ApUser apUser = new ApUser();
       apUser.setName(signUpRequest.getName());
       apUser.setUserDesignation(userDesignation);
       apUser.setEmail(signUpRequest.getEmail());
       apUser.setZone(zone);
       apUser.setActive(true);
       apUser.setUserType(userType);
       return apUser;
    }

    @Override
    public ApUser toEntity(AddUserRequest addUserRequest, UserType userType, UserDesignation userDesignation, Zone zone) {
        if(addUserRequest == null){
            return null;
        }
        ApUser apUser = new ApUser();
        apUser.setName(addUserRequest.getName());
        apUser.setUserDesignation(userDesignation);
        apUser.setEmail(addUserRequest.getEmail());
        apUser.setZone(zone);
        apUser.setActive(true);
        apUser.setUserType(userType);
        return apUser;
    }

}
