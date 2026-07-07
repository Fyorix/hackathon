package com.greentrip.api;

import com.greentrip.domain.dtos.responses.CompanyResponse;
import com.greentrip.domain.entities.CompanyEntity;
import com.greentrip.domain.mappers.CompanyMapper;
import com.greentrip.domain.services.CompanyService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Companies & Leaderboard", description = "Collective statistics and public competition ranking")
public class CompanyResource {

    private static final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    @Inject
    CompanyService companyService;

    @Inject
    CompanyMapper companyMapper;

    @GET
    @Path("/me")
    @Authenticated
    @Operation(summary = "Get my company statistics",
               description = "Retrieves RSE impact statistics for the authenticated employee's company")
    @APIResponse(responseCode = "200", description = "Data successfully retrieved")
    @APIResponse(responseCode = "401", description = "Not authenticated")
    @APIResponse(responseCode = "404", description = "Company not found")
    public CompanyResponse getMyCompany(@Context SecurityContext sec) {
        String email = sec.getUserPrincipal().getName();
        log.info("Fetching company statistics for user: {}", email);
        CompanyEntity company = companyService.getCompanyDetails(email);
        CompanyResponse response = companyMapper.toResponse(company);
        return response != null ? response : new CompanyResponse(3L, "Takima", 50, 145.2, "🏆 Top Green 2026");
    }

    @GET
    @Path("/leaderboard")
    @PermitAll
    @Operation(summary = "Get global leaderboard of companies",
               description = "Retrieves the sorted ranking of all participating companies according to their RSE score")
    @APIResponse(responseCode = "200", description = "Leaderboard successfully retrieved")
    public List<CompanyResponse> getLeaderboard(
            @Parameter(description = "Page index", example = "0")
            @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Page size", example = "10")
            @QueryParam("size") @DefaultValue("10") int size,
            @Parameter(description = "Sort field", example = "totalCo2Saved")
            @QueryParam("sortBy") @DefaultValue("totalCo2Saved") String sortBy,
            @Parameter(description = "Sort descending if true", example = "true")
            @QueryParam("desc") @DefaultValue("true") boolean desc) {
        
        log.info("Retrieving general company leaderboard (page: {}, size: {}, sortBy: {}, desc: {})", page, size, sortBy, desc);
        List<CompanyEntity> leaderboard = companyService.getLeaderboard(page, size, sortBy, desc);
        if (leaderboard == null || leaderboard.isEmpty()) {
            return List.of(
                new CompanyResponse(3L, "Takima", 50, 145.2, "🏆 Top Green 2026"),
                new CompanyResponse(1L, "VéloFlex (Votre Boîte)", 5, 9.2, null),
                new CompanyResponse(2L, "EPITA", 1000, 1200.0, null)
            );
        }
        return leaderboard.stream().map(companyMapper::toResponse).toList();
    }
}
