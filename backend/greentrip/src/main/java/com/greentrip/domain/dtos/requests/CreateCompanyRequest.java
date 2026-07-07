package com.greentrip.domain.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CreateCompanyRequest", description = "Payload sent to create a new company")
public record CreateCompanyRequest(
    @NotBlank(message = "Company name cannot be empty")
    @Schema(description = "Name of the company", examples = "Takima")
    String companyName,

    @NotBlank(message = "SIREN number cannot be empty")
    @Schema(description = "SIREN number of the company", examples = "123456789")
    String sirenNumber,

    @Schema(description = "Path to the company's logo image", examples = "/images/logos/takima.png")
    String logoPath
) {}
