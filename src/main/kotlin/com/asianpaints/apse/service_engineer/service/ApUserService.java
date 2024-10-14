package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.*;
import com.asianpaints.apse.service_engineer.dto.ApUserDto;
import com.asianpaints.apse.service_engineer.dto.ApiResponse;
import com.asianpaints.apse.service_engineer.dto.SignUpRequest;
import com.asianpaints.apse.service_engineer.exception.UserNotFoundException;
import com.asianpaints.apse.service_engineer.mapper.ApUserMapper;
import com.asianpaints.apse.service_engineer.repository.*;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApUserService {

    private final ApUserRepository apUserRepository;
    private final ApUserMapper apUserMapper;
    private final UserDesignationRepository userDesignationRepository;
    private final UserTypeRepository userTypeRepository;
    private final ZoneRepository zoneRepository;
    private final TokenRepository tokenRepository;

    public ApUserService(ApUserRepository apUserRepository,
                         ApUserMapper apUserMapper,
                         UserDesignationRepository userDesignationRepository,
                         UserTypeRepository userTypeRepository,
                         ZoneRepository zoneRepository,
                         TokenRepository tokenRepository){
        this.apUserRepository = apUserRepository;
        this.apUserMapper = apUserMapper;
        this.userDesignationRepository = userDesignationRepository;
        this.userTypeRepository = userTypeRepository;
        this.zoneRepository = zoneRepository;
        this.tokenRepository = tokenRepository;
    }

    public ApiResponse signUp(SignUpRequest signUpRequest){
        UserType userType = userTypeRepository.findByUserType("Service Engineer").orElse(null);
        UserDesignation userDesignation = userDesignationRepository.findById(signUpRequest.getDesignationId()).orElse(null);
        Zone zone = zoneRepository.findById(signUpRequest.getZoneId()).orElse(null);
        ApUser signupUser = apUserMapper.toEntity(signUpRequest, userType,userDesignation,zone);
        apUserRepository.save(signupUser);
        return ApiResponse.builder()
                .message("User signUp successfully")
                .status("Success")
                .build();
    }

    public ApUser addUser(ApUserDto addUserRequest){
        UserType userType = userTypeRepository.findById(addUserRequest.getUserTypeId()).orElse(null);
        UserDesignation userDesignation = userDesignationRepository.findById(addUserRequest.getDesignationId()).orElse(null);
        Zone zone = zoneRepository.findById(addUserRequest.getZoneId()).orElse(null);
        ApUser signupUser = apUserMapper.toEntity(addUserRequest, userType,userDesignation,zone);
        ApUser savedUser = apUserRepository.save(signupUser);
        return savedUser;
    }

    public ApUser editUser(Long userId, ApUserDto addUserRequest){
        ApUser apUser= apUserRepository.findById(userId).orElse(null);
        if(apUser == null){
            String errMsg = String.format("User with id %s does not exist in system", userId);
            throw new UserNotFoundException(errMsg);
        }
        UserType userType = userTypeRepository.findById(addUserRequest.getUserTypeId()).orElse(null);
        UserDesignation userDesignation = userDesignationRepository.findById(addUserRequest.getDesignationId()).orElse(null);
        Zone zone = zoneRepository.findById(addUserRequest.getZoneId()).orElse(null);
        apUser.setName(addUserRequest.getName());
        apUser.setUserDesignation(userDesignation);
        apUser.setEmail(addUserRequest.getEmail());
        apUser.setZone(zone);
        apUser.setUserType(userType);
        return apUserRepository.save(apUser);
    }

    public void deleteUser(Long userId){
        ApUser apUser= apUserRepository.findById(userId).orElse(null);
        if(apUser == null){
            String errMsg = String.format("User with id %s does not exist in system", userId);
            throw new UserNotFoundException(errMsg);
        }
        revokeAllUserTokens(apUser);
        apUser.setActive(false);
        apUserRepository.save(apUser);
    }

    public ApUser findUserByEmail(String email){
        ApUser apUser = apUserRepository.findByEmail(email);
        if(apUser == null)
            return null;
        UserDesignation userDesignation = userDesignationRepository.findById(apUser.getUserDesignation().getId()).orElse(null);
        apUser.setUserDesignation(userDesignation);
        return apUser;
    }

    public ApUser getUser(Long userId){
        ApUser apUser= apUserRepository.findById(userId).orElse(null);
        if(apUser == null){
            String errMsg = String.format("User with id %s does not exist in system", userId);
            throw new UserNotFoundException(errMsg);
        }
        return apUser;
    }

    public List<ApUser> getAllUserUsers(){
        return apUserRepository.findAll();
    }


    public List<UserDesignation> getAllUserDesignations(){
        return userDesignationRepository.findAll();
    }

    public List<UserType> getAllUserTypes(){
        return userTypeRepository.findAll();
    }

    private String getError(Set<ConstraintViolation<SignUpRequest>> violations){
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

    }

    private void revokeAllUserTokens(ApUser user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        for (Token t : validUserTokens) {
            t.setExpired(true);
            t.setRevoked(true);
        }
        tokenRepository.saveAll(validUserTokens);
    }

}
