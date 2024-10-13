package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.UserDesignation;
import com.asianpaints.apse.service_engineer.domain.entity.UserType;
import com.asianpaints.apse.service_engineer.dto.AddUserRequest;
import com.asianpaints.apse.service_engineer.dto.EditUserRequest;
import com.asianpaints.apse.service_engineer.dto.SignUpRequest;
import com.asianpaints.apse.service_engineer.exception.UserNotFoundException;
import com.asianpaints.apse.service_engineer.service.ApUserService;
import com.asianpaints.apse.service_engineer.validator.AddUserRequestValidator;
import com.asianpaints.apse.service_engineer.validator.EditUserRequestValidator;
import com.asianpaints.apse.service_engineer.validator.SignUpRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class ApUserController {

    private final ApUserService apUserService;
    private final SignUpRequestValidator signUpRequestValidator;
    private final AddUserRequestValidator addUserRequestValidator;
    private final EditUserRequestValidator editUserRequestValidator;

    public ApUserController(ApUserService apUserService,
                            SignUpRequestValidator signUpRequestValidator,
                            AddUserRequestValidator addUserRequestValidator,
                            EditUserRequestValidator editUserRequestValidator){
        this.apUserService = apUserService;
        this.signUpRequestValidator = signUpRequestValidator;
        this.addUserRequestValidator = addUserRequestValidator;
        this.editUserRequestValidator = editUserRequestValidator;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignUpRequest signUpRequest){

        List<String> validationErrors = signUpRequestValidator.validate(signUpRequest);
        if(!validationErrors.isEmpty()){
            String errorMsg = String.join(",", validationErrors);
            return ResponseEntity.badRequest().body(errorMsg);
        }
        try {
            ApUser apUser = apUserService.signUp(signUpRequest);
            return ResponseEntity.ok(apUser);
        }
        catch (IllegalArgumentException ex){

            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> addUser(@RequestBody AddUserRequest addUserRequest){

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
    @PutMapping()
    public ResponseEntity<Object> editUser(@RequestBody EditUserRequest editUserRequest){

        List<String> validationErrors = editUserRequestValidator.validate(editUserRequest);
        if(!validationErrors.isEmpty()){
            String errorMsg = String.join(",", validationErrors);
            return ResponseEntity.badRequest().body(errorMsg);
        }
        try {
            ApUser apUser = apUserService.editUser(editUserRequest);
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

        try {
            apUserService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
     }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable Long userId){

        try {
            ApUser user = apUserService.getUser(userId);
            return ResponseEntity.ok(user);
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(){
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
}
