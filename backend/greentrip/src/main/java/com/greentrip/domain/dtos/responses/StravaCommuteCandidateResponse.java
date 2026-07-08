package com.greentrip.domain.dtos.responses;

import com.greentrip.domain.entities.TransportType;
import java.time.LocalDate;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "StravaCommuteCandidateResponse",
        description = "Strava activity starting or ending within 500m of the user's workplace, eligible for import as a trip")
public record StravaCommuteCandidateResponse(
    @Schema(description = "Strava activity ID") Long stravaActivityId,
    @Schema(description = "Activity name on Strava") String name,
    @Schema(description = "Transport mode inferred from the Strava activity type") TransportType type,
    @Schema(description = "Distance in kilometers") Double distanceKm,
    @Schema(description = "Local date the activity took place") LocalDate date,
    @Schema(description = "Whether the activity starts within 500m of the workplace") boolean startsNearWork,
    @Schema(description = "Whether the activity ends within 500m of the workplace") boolean endsNearWork
) {}
