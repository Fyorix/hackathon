package com.greentrip.dtos.requests;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "RegisterRequest", description = "Payload sent to register a new user")
public record RegisterRequest(
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Schema(description = "User's display name", example = "Alex")
    String name,

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Schema(description = "User's email address", example = "alex@takima.fr")
    String email,

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "User's secure password", example = "supersecret")
    String password,

    @NotNull(message = "Company ID is required")
    @Schema(description = "ID of the company the user belongs to", example = "3")
    Long companyId
) {}
