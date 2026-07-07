package com.greentrip.domain.dtos.responses;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CompanyResponse", description = "Company details returned by the API")
public record CompanyResponse(
    @Schema(description = "Unique identifier", examples = "3")
    Long id,
    @Schema(description = "Name of the company", examples = "Takima")
    String name,
    @Schema(description = "SIREN number of the company", examples = "123456789")
    String sirenNumber,
    @Schema(description = "Total number of employees", examples = "50")
    Integer totalEmployees,
    @Schema(description = "Total CO2 saved by all employees in kilograms", examples = "145.2")
    Double totalCo2Saved,
    @Schema(description = "Total points accumulated by the company", examples = "1200")
    Integer totalPoints,
    @Schema(description = "Total kilometers traveled by all employees", examples = "3500.5")
    Double totalKm,
    @Schema(description = "Path to the company's logo image", examples = "/images/logos/takima.png")
    String logoPath
) {}
