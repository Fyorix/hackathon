package com.greentrip.domain.dtos.responses;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "TokenResponse", description = "Token details returned after successful authentication")
public record TokenResponse(
    @Schema(description = "JWT Bearer token to include in the Authorization header", examples = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    String token,
    @Schema(description = "Token type", examples = "Bearer")
    String tokenType,
    @Schema(description = "Expiration duration in seconds", examples = "3600")
    Long expiresIn
) {}
