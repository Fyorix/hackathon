package com.greentrip.api;

import com.greentrip.domain.dtos.requests.CreateCompanyRequest;
import com.greentrip.domain.dtos.requests.UpdateCompanyRequest;
import com.greentrip.domain.dtos.responses.CompanyResponse;
import com.greentrip.domain.dtos.responses.UserResponse;
import com.greentrip.domain.entities.CompanyEntity;
import com.greentrip.domain.entities.CompanySortField;
import com.greentrip.domain.entities.UserEntity;
import com.greentrip.domain.entities.UserSortField;
import com.greentrip.domain.mappers.CompanyMapper;
import com.greentrip.domain.mappers.UserMapper;
import com.greentrip.domain.services.CompanyService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
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

    @Inject
    UserMapper userMapper;

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
        return response != null ? response : new CompanyResponse(3L, "Takima", "123456789", 50, 145.2, 1200, 3500.5, "/images/logos/takima.png");
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Operation(summary = "Get a company by id",
               description = "Retrieves a single company's details")
    @APIResponse(responseCode = "200", description = "Company successfully retrieved")
    @APIResponse(responseCode = "404", description = "Company not found")
    public CompanyResponse getCompany(@PathParam("id") Long id) {
        log.info("Fetching company by id: {}", id);
        CompanyEntity company = companyService.getCompanyById(id);
        return companyMapper.toResponse(company);
    }

    @POST
    @RolesAllowed("ADMIN")
    @Operation(summary = "Create a company (Admin)",
               description = "Registers a new company. Requires administrative privileges.")
    @APIResponse(responseCode = "201", description = "Company successfully created")
    @APIResponse(responseCode = "400", description = "Invalid request payload")
    @APIResponse(responseCode = "409", description = "Company name or SIREN number already in use")
    public Response createCompany(@Valid CreateCompanyRequest request) {
        log.info("Creating new company: {}", request.companyName());
        CompanyEntity created = companyService.createCompany(request);
        CompanyResponse response = companyMapper.toResponse(created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Operation(summary = "Update a company (Admin)",
               description = "Updates the name and/or logo of an existing company. Requires administrative privileges.")
    @APIResponse(responseCode = "200", description = "Company successfully updated")
    @APIResponse(responseCode = "404", description = "Company not found")
    @APIResponse(responseCode = "409", description = "Company name already in use")
    public CompanyResponse updateCompany(@PathParam("id") Long id, @Valid UpdateCompanyRequest request) {
        log.info("Updating company id: {}", id);
        CompanyEntity updated = companyService.updateCompany(id, request);
        return companyMapper.toResponse(updated);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Operation(summary = "Delete a company (Admin)",
               description = "Deletes a company. Requires administrative privileges.")
    @APIResponse(responseCode = "204", description = "Company successfully deleted")
    @APIResponse(responseCode = "404", description = "Company not found")
    public Response deleteCompany(@PathParam("id") Long id) {
        log.info("Deleting company id: {}", id);
        companyService.deleteCompany(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/search")
    @PermitAll
    @Operation(summary = "Search companies by name",
               description = "Returns a paginated list of companies matching the query (at least 4 characters).")
    @APIResponse(responseCode = "200", description = "Search results successfully retrieved")
    @APIResponse(responseCode = "400", description = "Query length too short")
    public List<CompanyResponse> searchCompanies(
            @Parameter(description = "Search query (min 4 characters)", example = "Taki")
            @QueryParam("query") String query,
            @Parameter(description = "Page index", example = "0")
            @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Page size", example = "10")
            @QueryParam("size") @DefaultValue("10") int size) {
        
        log.info("Searching companies via API (query: '{}', page: {}, size: {})", query, page, size);
        List<CompanyEntity> results = companyService.searchCompanies(query, page, size);
        return results.stream().map(companyMapper::toResponse).toList();
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
            @Parameter(description = "Sort field", example = "CO2")
            @QueryParam("sortBy") @DefaultValue("CO2") CompanySortField sortBy,
            @Parameter(description = "Sort descending if true", example = "true")
            @QueryParam("desc") @DefaultValue("true") boolean desc) {
        
        log.info("Retrieving general company leaderboard (page: {}, size: {}, sortBy: {}, desc: {})", page, size, sortBy, desc);
        List<CompanyEntity> leaderboard = companyService.getLeaderboard(page, size, sortBy, desc);
        return leaderboard.stream().map(companyMapper::toResponse).toList();
    }

    @GET
    @Path("/{id}/leaderboard")
    @PermitAll
    @Operation(summary = "Get user leaderboard for a company",
               description = "Retrieves the sorted and paged ranking of all users/employees of a specific company")
    @APIResponse(responseCode = "200", description = "User leaderboard successfully retrieved")
    @APIResponse(responseCode = "404", description = "Company not found")
    public List<UserResponse> getCompanyUserLeaderboard(
            @PathParam("id") Long id,
            @Parameter(description = "Page index", example = "0")
            @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Page size", example = "10")
            @QueryParam("size") @DefaultValue("10") int size,
            @Parameter(description = "Sort field (POINTS, CO2, KM)", example = "CO2")
            @QueryParam("sortBy") @DefaultValue("CO2") UserSortField sortBy,
            @Parameter(description = "Sort descending if true", example = "true")
            @QueryParam("desc") @DefaultValue("true") boolean desc) {
        
        log.info("Retrieving user leaderboard for company id: {} (page: {}, size: {}, sortBy: {}, desc: {})", id, page, size, sortBy, desc);
        List<UserEntity> leaderboard = companyService.getCompanyUserLeaderboard(id, page, size, sortBy, desc);
        return leaderboard.stream().map(userMapper::toResponse).toList();
    }

    @GET
    @Path("/{id}/leaderboard/search")
    @PermitAll
    @Operation(summary = "Search users within a company leaderboard",
               description = "Returns a paginated list of users in a company matching the search query (min 4 characters).")
    @APIResponse(responseCode = "200", description = "Search results successfully retrieved")
    @APIResponse(responseCode = "400", description = "Query length too short")
    @APIResponse(responseCode = "404", description = "Company not found")
    public List<UserResponse> searchCompanyUsers(
            @PathParam("id") Long id,
            @Parameter(description = "Search query (min 4 characters)", example = "Alex")
            @QueryParam("query") String query,
            @Parameter(description = "Page index", example = "0")
            @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Page size", example = "10")
            @QueryParam("size") @DefaultValue("10") int size) {
        
        log.info("Searching users in company ID {} leaderboard via API (query: '{}', page: {}, size: {})", id, query, page, size);
        List<UserEntity> results = companyService.searchCompanyUsers(id, query, page, size);
        return results.stream().map(userMapper::toResponse).toList();
    }
}
