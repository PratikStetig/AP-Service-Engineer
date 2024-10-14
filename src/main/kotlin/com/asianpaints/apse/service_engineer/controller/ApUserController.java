package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.UserDesignation;
import com.asianpaints.apse.service_engineer.domain.entity.UserType;
import com.asianpaints.apse.service_engineer.dto.ApUserDto;
import com.asianpaints.apse.service_engineer.dto.ApiResponse;
import com.asianpaints.apse.service_engineer.dto.SignUpRequest;
import com.asianpaints.apse.service_engineer.exception.UserNotFoundException;
import com.asianpaints.apse.service_engineer.service.ApUserService;
import com.asianpaints.apse.service_engineer.service.PreAuthorizeAuthorizationService;
import com.asianpaints.apse.service_engineer.validator.AddUserRequestValidator;
import com.asianpaints.apse.service_engineer.validator.EditUserRequestValidator;
import com.asianpaints.apse.service_engineer.validator.SignUpRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class ApUserController {

    private final ApUserService apUserService;
    private final SignUpRequestValidator signUpRequestValidator;
    private final AddUserRequestValidator addUserRequestValidator;
    private final EditUserRequestValidator editUserRequestValidator;
    private final PreAuthorizeAuthorizationService preAuthorizeAuthorizationService;

//    public ApUserController(ApUserService apUserService,
//                            SignUpRequestValidator signUpRequestValidator,
//                            AddUserRequestValidator addUserRequestValidator,
//                            EditUserRequestValidator editUserRequestValidator){
//        this.apUserService = apUserService;
//        this.signUpRequestValidator = signUpRequestValidator;
//        this.addUserRequestValidator = addUserRequestValidator;
//        this.editUserRequestValidator = editUserRequestValidator;
//    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignUpRequest signUpRequest){

        List<String> validationErrors = signUpRequestValidator.validate(signUpRequest);
        if(!validationErrors.isEmpty()){
            String errorMsg = String.join(",", validationErrors);
            return ResponseEntity.badRequest().body(errorMsg);
        }
        try {
            ApiResponse apiResponse = apUserService.signUp(signUpRequest);
            return ResponseEntity.ok(apiResponse);
        }
        catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> addUser(@RequestBody ApUserDto addUserRequest){
        AuthorizationDecision authorizationDecision = checkAuthorization();
        if(!authorizationDecision.isGranted()) {
            return ResponseEntity.status(403).build();
        }
        List<String> validationErrors = addUserRequestValidator.validate(addUserRequest);
        if(!validationErrors.isEmpty()){
            String errorMsg = String.join(",", validationErrors);
            return ResponseEntity.badRequest().body(errorMsg);
        }
        try {
            ApUser apUser = apUserService.addUser(addUserRequest);
            return ResponseEntity.ok(apUser);
        }
        catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PutMapping("/{userId}")
    public ResponseEntity<Object> editUser(@PathVariable Long userId, @RequestBody ApUserDto addUserRequest){
        AuthorizationDecision authorizationDecision = checkAuthorization();
        if(!authorizationDecision.isGranted()) {
            return ResponseEntity.status(403).build();
        }
        List<String> validationErrors = editUserRequestValidator.validate(userId,addUserRequest);
        if(!validationErrors.isEmpty()){
            String errorMsg = String.join(",", validationErrors);
            return ResponseEntity.badRequest().body(errorMsg);
        }
        try {
            ApUser apUser = apUserService.editUser(userId,addUserRequest);
            return ResponseEntity.ok(apUser);
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId){
        AuthorizationDecision authorizationDecision = checkAuthorization();
        if(!authorizationDecision.isGranted()) {
            return ResponseEntity.status(403).build();
        }
        try {
            apUserService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
     }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable("userId") Long userId){
        AuthorizationDecision authorizationDecision = checkAuthorization();
        if(!authorizationDecision.isGranted()) {
            return ResponseEntity.status(403).build();
        }
        try {
            ApUser user = apUserService.getUser(userId);
            return ResponseEntity.ok(user);
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllUsers(){
       AuthorizationDecision authorizationDecision = checkAuthorization();
       if(!authorizationDecision.isGranted()) {
           return ResponseEntity.status(403).build();
       }
       List<ApUser> users = apUserService.getAllUserUsers();
       return ResponseEntity.ok(users);

    }

    @GetMapping("/designation")
    public ResponseEntity<Object> getAllUserDesignations(){
        List<UserDesignation> designations = apUserService.getAllUserDesignations();
        return ResponseEntity.ok(designations);
    }

    @GetMapping("/type")
    public ResponseEntity<Object> getAllUserTypes(){
        List<UserType> userTypes = apUserService.getAllUserTypes();
        return ResponseEntity.ok(userTypes);
    }

    private AuthorizationDecision checkAuthorization(){
        return preAuthorizeAuthorizationService.check("Service Engineer");
    }
}
