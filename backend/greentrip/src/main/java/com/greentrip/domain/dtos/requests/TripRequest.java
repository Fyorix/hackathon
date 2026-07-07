package com.greentrip.domain.dtos.requests;

import com.greentrip.domain.entities.TransportType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "TripRequest", description = "Payload sent to declare a new green trip")
public record TripRequest(
    @NotNull(message = "Distance cannot be null")
    @Positive(message = "Distance must be strictly greater than 0")
    @Schema(description = "Distance traveled in kilometers", examples = {"12.0"})
    Double distanceKm,

    @NotNull(message = "Transport type is required")
    @Schema(description = "Mode of transportation used", examples = {"VELO"})
    TransportType type
) {}
