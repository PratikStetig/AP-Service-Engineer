package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.Token;
import com.asianpaints.apse.service_engineer.domain.entity.TokenType;
import com.asianpaints.apse.service_engineer.dto.AuthenticationRequest;
import com.asianpaints.apse.service_engineer.dto.ApiResponse;
import com.asianpaints.apse.service_engineer.dto.AuthenticationResponse;
import com.asianpaints.apse.service_engineer.exception.AuthenticationFailedException;
import com.asianpaints.apse.service_engineer.exception.EmailVerificationFailedException;
import com.asianpaints.apse.service_engineer.exception.UserNotFoundException;
import com.asianpaints.apse.service_engineer.jwt.JwtUtils;
import com.asianpaints.apse.service_engineer.model.EmailDetails;
import com.asianpaints.apse.service_engineer.repository.TokenRepository;
import com.asianpaints.apse.service_engineer.repository.UserDesignationRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthenticationService {

    private OtpService otpService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtTokenUtil;
    private UserDetailsService userDetailsService;
    private ApUserService apUserService;
    private TokenRepository tokenRepository;
    private EmailService emailService;
    private UserDesignationRepository userDesignationRepository;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtUtils jwtTokenUtil,
                                 UserDetailsService userDetailsService,
                                 OtpService otpService,
                                 ApUserService apUserService,
                                 TokenRepository tokenRepository,
                                 EmailService emailService) {
        this.otpService = otpService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.apUserService = apUserService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public ApiResponse generateOTP(String email, boolean isSignupVerification) {
        if (!isSignupVerification)
            checkUserExist(email);

        try {
            String otp = otpService.generateOtp(email);
            if (isSignupVerification)
                sendSignUpVerificationMail(otp, email);
            else
                sendVerificationMail(otp, email);

            return ApiResponse.builder()
                    .message("OTP sent successfully")
                    .status("Success")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("Issue in sending OTP")
                    .status("success")
                    .build();
        }
    }

    public ApiResponse signUpEmailVerification(AuthenticationRequest authenticationRequest) {

        if (authenticationRequest.getOtp().equals(otpService.getCacheOtp(authenticationRequest.getEmail()))) {
            return ApiResponse.builder()
                    .message("Email verified successfully")
                    .status("Success")
                    .build();
        }
        throw new EmailVerificationFailedException();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        ApUser apUser = checkUserExist(authenticationRequest.getEmail());
        try {
            if (authenticationRequest.getOtp().equals(otpService.getCacheOtp(authenticationRequest.getEmail()))) {
                String jwtToken = createAuthenticationToken(authenticationRequest);
                revokeAllUserTokens(apUser);
                saveUserToken(apUser, jwtToken);
                otpService.clearOtp(authenticationRequest.getEmail());
                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .userName(apUser.getName())
                        .designation(apUser.getUserDesignation().getDesignation())
                        .build();
            }
            throw new AuthenticationFailedException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String createAuthenticationToken(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), "")
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect email or OTP", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        return jwtTokenUtil.generateTokenFromUserName(userDetails);
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

    private ApUser checkUserExist(String email) {
        ApUser user = apUserService.findUserByEmail(email);

        if (user == null || !user.isActive()) {
            String errMsg = String.format("User with email %s does not exist in system", email);
            throw new UserNotFoundException(errMsg);
        }
        return user;
    }

    private void saveUserToken(ApUser apUser, String jwtToken) {
        Token token = Token.builder()
                .user(apUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void sendSignUpVerificationMail(String otp, String email) {
        String otpMessage = "Dear Customer,<br><br> Your OTP is " + otp + ". Use this OTP for email verification.";
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setMsgBody(otpMessage);
        emailDetails.setToRecipient(email);
        emailDetails.setSubject("Your application email verification OTP details");

        emailService.sendMail(emailDetails);
    }

    private void sendVerificationMail(String otp, String email) {
        String otpMessage = "Dear Customer,<br><br> Your OTP is " + otp + ". Use this OTP to log in to Application";
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setMsgBody(otpMessage);
        emailDetails.setToRecipient(email);
        emailDetails.setSubject("Your application OTP details");

        emailService.sendMail(emailDetails);
    }
}
