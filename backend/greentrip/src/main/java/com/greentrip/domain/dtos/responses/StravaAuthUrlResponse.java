package com.greentrip.domain.dtos.responses;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "StravaAuthUrlResponse", description = "Strava OAuth2 authorization URL to redirect the user to")
public record StravaAuthUrlResponse(
    @Schema(description = "URL to redirect the user to for Strava consent", examples = {"https://www.strava.com/oauth/authorize?client_id=..."})
    String url
) {}
