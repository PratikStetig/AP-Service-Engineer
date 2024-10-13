package com.asianpaints.apse.service_engineer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "email field can't be blank")
    private String email;
    @NotBlank(message = "otp field can't be blank")
    private String otp;

}
