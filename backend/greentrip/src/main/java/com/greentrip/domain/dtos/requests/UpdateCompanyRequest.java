package com.greentrip.domain.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "UpdateCompanyRequest", description = "Payload sent to update an existing company")
public record UpdateCompanyRequest(
    @NotBlank(message = "Company name cannot be empty")
    @Schema(description = "Name of the company", examples = "Takima")
    String companyName,

    @Schema(description = "Path to the company's logo image", examples = "/images/logos/takima.png")
    String logoPath
) {}
