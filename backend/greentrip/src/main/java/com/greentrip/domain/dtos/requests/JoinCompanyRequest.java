package com.greentrip.domain.dtos.requests;

import java.time.LocalTime;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

@Schema(name = "JoinCompanyRequest", description = "Payload sent when a user joins a company and sets their commute preferences")
public record JoinCompanyRequest(
    @NotNull(message = "Company ID is required")
    @Schema(description = "ID of the company to join", examples = {"1"})
    Long companyId,

    @NotNull(message = "Work latitude is required")
    @Schema(description = "Latitude of work location", examples = {"48.8966"})
    Double workLat,

    @NotNull(message = "Work longitude is required")
    @Schema(description = "Longitude of work location", examples = {"2.3922"})
    Double workLng,

    @Schema(description = "Start time of work day", examples = {"09:00:00"})
    LocalTime workStartTime,

    @Schema(description = "End time of work day", examples = {"18:00:00"})
    LocalTime workEndTime
) {}
