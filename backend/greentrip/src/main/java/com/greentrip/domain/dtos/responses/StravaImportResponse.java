package com.greentrip.domain.dtos.responses;

import java.util.List;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "StravaImportResponse", description = "Result of importing selected Strava activities as trips")
public record StravaImportResponse(
    @Schema(description = "Trips successfully created") List<TripResponse> imported,
    @Schema(description = "Activities that were not imported, with the reason") List<Skipped> skipped
) {
    @Schema(name = "StravaImportSkipped")
    public record Skipped(
        @Schema(description = "Strava activity ID") Long stravaActivityId,
        @Schema(description = "Reason the activity was not imported") String reason
    ) {}
}
