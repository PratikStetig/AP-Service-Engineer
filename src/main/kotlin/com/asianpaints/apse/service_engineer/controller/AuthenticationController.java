package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.dto.ApiResponse;
import com.asianpaints.apse.service_engineer.dto.AuthenticationRequest;
import com.asianpaints.apse.service_engineer.dto.AuthenticationResponse;
import com.asianpaints.apse.service_engineer.exception.AuthenticationFailedException;
import com.asianpaints.apse.service_engineer.exception.EmailVerificationFailedException;
import com.asianpaints.apse.service_engineer.exception.UserNotFoundException;
import com.asianpaints.apse.service_engineer.service.AuthenticationService;
import com.asianpaints.apse.service_engineer.validator.AuthenticationRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationRequestValidator authenticationRequestValidator;

    @RequestMapping(value = "/signUp/requestOtp/{email}",method = RequestMethod.GET)
    public ResponseEntity<Object> generateOtp(@PathVariable String email){
        try{
            ApiResponse response = authenticationService.generateOTP(email,true);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/signUp/email/verification",method = RequestMethod.POST)
    public ResponseEntity<Object> signUpEmailVerification(@RequestBody AuthenticationRequest authenticationRequest){
        List<String> violations = authenticationRequestValidator.validate(authenticationRequest);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join(",", violations));
        }
        try{
            ApiResponse response = authenticationService.signUpEmailVerification(authenticationRequest);
            return ResponseEntity.ok(response);
        } catch (EmailVerificationFailedException e){
            return ResponseEntity.status(401).build();
        }
    }


    @RequestMapping(value = "/requestOtp/{email}",method = RequestMethod.GET)
    public ResponseEntity<Object> getOtp(@PathVariable String email){
            try{
                ApiResponse response = authenticationService.generateOTP(email,false);
                return ResponseEntity.ok(response);
            } catch (UserNotFoundException e){
                return ResponseEntity.notFound().build();
            }
    }


    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        List<String> violations = authenticationRequestValidator.validate(authenticationRequest);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join(",", violations));
        }
        try{
            AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationFailedException e){
            return ResponseEntity.status(401).build();
        }
        catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


}
