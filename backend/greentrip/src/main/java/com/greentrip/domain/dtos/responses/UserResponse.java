package com.greentrip.domain.dtos.responses;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "UserResponse", description = "User/employee details returned by the API")
public record UserResponse(
    @Schema(description = "Unique identifier", example = "1")
    Long id,
    @Schema(description = "Display name", example = "Alex")
    String name,
    @Schema(description = "Professional email address", example = "alex@takima.fr")
    String email,
    @Schema(description = "Role of the user", example = "USER")
    String role,
    @Schema(description = "Available carbon points balance", example = "450")
    Integer carbonPointsBalance,
    @Schema(description = "Total CO2 saved in kilograms", example = "9.2")
    Double totalCo2Saved
) {}
