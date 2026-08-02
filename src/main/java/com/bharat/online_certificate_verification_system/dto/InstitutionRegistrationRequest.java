package com.bharat.online_certificate_verification_system.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitutionRegistrationRequest {
    @NotBlank(message="Institution name is required")
    private String institutionName;

    @NotBlank(message= "Admin fulll name is required")
    private String fullName;

    @Email(message="Enter a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, message ="Password must contain at least 8 characters")
    @NotBlank(message = "Password id required")
    private String password;

    @NotBlank(message = "Phone number is required")
    private String phone;


    @NotBlank(message="website is required")
    private String website;

    @NotBlank(message="Address is required")
    private String address;
}
