package com.greentrip.dtos.requests;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "TripRequest", description = "Payload sent to declare a new green trip")
public record TripRequest(
    @NotNull(message = "Distance cannot be null")
    @Positive(message = "Distance must be strictly greater than 0")
    @Schema(description = "Distance traveled in kilometers", example = "12.0")
    Double distanceKm,

    @NotBlank(message = "Transport type is required")
    @Pattern(regexp = "^(VELO|TROTINETTE|MARCHE|COVOITURAGE|TRANSPORTS_COMMUNS)$", 
             message = "Invalid transport type")
    @Schema(description = "Mode of transportation used", example = "VELO")
    String type
) {}
