package com.greentrip.domain.dtos.responses;

import com.greentrip.domain.entities.TransportType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "TripResponse", description = "Trip details returned by the API")
public record TripResponse(
    @Schema(description = "Unique identifier of the trip", examples = "13")
    Long id,
    @Schema(description = "Distance traveled in kilometers", examples = "12.0")
    Double distanceKm,
    @Schema(description = "Amount of CO2 saved in kilograms", examples = "2.4")
    Double co2Saved,
    @Schema(description = "Number of carbon points earned", examples = "120")
    Integer pointsEarned,
    @Schema(description = "Mode of transportation used", examples = "VELO")
    TransportType type,
    @Schema(description = "Timestamp of registration")
    LocalDateTime createdAt
) {}
