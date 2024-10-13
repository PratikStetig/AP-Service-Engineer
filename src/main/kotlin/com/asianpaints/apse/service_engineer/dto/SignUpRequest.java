package com.asianpaints.apse.service_engineer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "name field can't be blank")
    private String name;
    @NotNull(message = "zoneId field can't be blank")
    private Long zoneId;
    @NotBlank(message = "email field can't be blank")
    private String email;
    @NotNull(message = "designationId field can't be blank")
    private Long designationId;

}
