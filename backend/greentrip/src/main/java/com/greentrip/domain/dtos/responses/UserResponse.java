package com.greentrip.domain.dtos.responses;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "UserResponse", description = "User/employee details returned by the API")
public record UserResponse(
    @Schema(description = "Unique identifier", examples = {"1"})
    Long id,
    @Schema(description = "Display name", examples = {"Alex"})
    String name,
    @Schema(description = "Professional email address", examples = {"alex@takima.fr"})
    String email,
    @Schema(description = "Role of the user", examples = {"USER"})
    String role,
    @Schema(description = "Available carbon points balance", examples = {"450"})
    Integer carbonPointsBalance,
    @Schema(description = "Total CO2 saved in kilograms", examples = {"9.2"})
    Double totalCo2Saved,
    @Schema(description = "Total distance traveled in kilometers", examples = {"120.5"})
    Double totalKm
) {}
