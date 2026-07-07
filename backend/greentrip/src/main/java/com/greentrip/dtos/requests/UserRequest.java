package com.greentrip.dtos.requests;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "UserRequest", description = "Payload sent to update a user profile")
public record UserRequest(
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Schema(description = "Display name", example = "Alex")
    String name,

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Schema(description = "Professional email address", example = "alex@takima.fr")
    String email
) {}
