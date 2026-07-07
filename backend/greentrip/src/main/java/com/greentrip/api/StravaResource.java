package com.greentrip.api;

import com.greentrip.domain.dtos.requests.StravaCallbackRequest;
import com.greentrip.domain.dtos.responses.StravaAuthUrlResponse;
import com.greentrip.domain.services.StravaService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.net.URI;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/strava")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@Tag(name = "Strava", description = "Link a user account to Strava via OAuth2")
public class StravaResource {

    private static final Logger log = LoggerFactory.getLogger(StravaResource.class);

    @Inject
    StravaService stravaService;

    @ConfigProperty(name = "app.dashboard-url")
    String dashboardUrl;

    @GET
    @Path("/auth-url")
    @Operation(summary = "Get the Strava authorization URL",
               description = "Generates the Strava OAuth2 consent URL (client_id + scopes) to redirect the user to.")
    @APIResponse(responseCode = "200", description = "Authorization URL generated")
    public StravaAuthUrlResponse getAuthUrl() {
        log.info("Generating Strava authorization URL");
        return new StravaAuthUrlResponse(stravaService.getAuthorizationUrl());
    }

    @POST
    @Path("/callback")
    @Operation(summary = "Handle the Strava OAuth2 callback",
               description = "Exchanges the authorization code for a refresh token, stores it on the user account, "
                       + "and redirects to the application dashboard.")
    @APIResponse(responseCode = "303", description = "Account linked, redirecting to the dashboard")
    @APIResponse(responseCode = "404", description = "Authenticated user not found")
    @APIResponse(responseCode = "502", description = "Strava API unreachable or rejected the code")
    public Response callback(@Context SecurityContext sec, @Valid StravaCallbackRequest request) {
        String email = sec.getUserPrincipal().getName();
        log.info("Handling Strava OAuth2 callback for user: {}", email);
        stravaService.linkAccount(email, request.code());
        return Response.seeOther(URI.create(dashboardUrl)).build();
    }
}
