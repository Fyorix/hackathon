package com.greentrip.domain.dtos.requests;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "StravaCallbackRequest", description = "Authorization code returned by Strava after user consent")
public record StravaCallbackRequest(
    @NotBlank(message = "Authorization code cannot be empty")
    @Schema(description = "Authorization code issued by Strava", examples = {"a1b2c3d4e5f6"})
    String code
) {}
