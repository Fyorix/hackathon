package com.greentrip.domain.dtos.requests;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "LoginRequest", description = "Payload sent to log in")
public record LoginRequest(
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Schema(description = "User's email address", examples = {"alex@takima.fr"})
    String email,

    @NotBlank(message = "Password cannot be empty")
    @Schema(description = "User's secure password", examples = {"supersecret"})
    String password
) {}
