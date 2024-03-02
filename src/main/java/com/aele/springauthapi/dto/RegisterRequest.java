package com.aele.springauthapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    @NotBlank
    private String password;
}
