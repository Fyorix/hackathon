package com.greentrip.domain.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "StravaImportRequest", description = "Strava activity IDs selected by the user to import as trips")
public record StravaImportRequest(
    @NotEmpty(message = "Select at least one activity")
    @Schema(description = "IDs of the Strava activities to import, from the commute-candidates list")
    List<Long> stravaActivityIds
) {}
