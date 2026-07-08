package com.greentrip.api;

import com.greentrip.domain.dtos.requests.StravaImportRequest;
import com.greentrip.domain.dtos.responses.StravaAuthUrlResponse;
import com.greentrip.domain.dtos.responses.StravaCommuteCandidateResponse;
import com.greentrip.domain.dtos.responses.StravaImportResponse;
import com.greentrip.domain.services.StravaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/strava")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Strava", description = "Link a user account to Strava via OAuth2 (hackathon: no auth, pass X-User-Email header)")
public class StravaResource {

    private static final Logger log = LoggerFactory.getLogger(StravaResource.class);

    @Inject
    StravaService stravaService;

    @ConfigProperty(name = "app.dashboard-url")
    String dashboardUrl;

    @GET
    @Path("/auth-url")
    @Operation(summary = "Get the Strava authorization URL",
               description = "Generates the Strava OAuth2 consent URL (client_id + scopes) to redirect the user to. "
                       + "Hackathon mode: no auth, identify the user via the X-User-Email header; "
                       + "it is embedded in the OAuth 'state' param so the callback (a plain browser "
                       + "redirect from Strava, which cannot carry custom headers) can recover it.")
    @APIResponse(responseCode = "200", description = "Authorization URL generated")
    @APIResponse(responseCode = "400", description = "Missing X-User-Email header")
    public StravaAuthUrlResponse getAuthUrl(@HeaderParam("X-User-Email") String email) {
        requireEmail(email);
        log.info("Generating Strava authorization URL for user: {}", email);
        String state = Base64.getUrlEncoder().withoutPadding().encodeToString(email.getBytes(StandardCharsets.UTF_8));
        return new StravaAuthUrlResponse(stravaService.getAuthorizationUrl(state));
    }

    @GET
    @Path("/callback")
    @Operation(summary = "Handle the Strava OAuth2 callback",
               description = "Exchanges the authorization code for a refresh token, stores it on the user account, "
                       + "and redirects to the application dashboard. Strava redirects here via a plain browser "
                       + "GET, so the user is recovered from the 'state' param set in /auth-url.")
    @APIResponse(responseCode = "303", description = "Account linked, redirecting to the dashboard")
    @APIResponse(responseCode = "400", description = "Missing or invalid code/state")
    @APIResponse(responseCode = "404", description = "User not found")
    @APIResponse(responseCode = "502", description = "Strava API unreachable or rejected the code")
    public Response callback(@QueryParam("code") String code, @QueryParam("state") String state) {
        if (code == null || code.isBlank() || state == null || state.isBlank()) {
            throw new BadRequestException("Missing code or state");
        }
        String email = new String(Base64.getUrlDecoder().decode(state), StandardCharsets.UTF_8);
        log.info("Handling Strava OAuth2 callback for user: {}", email);
        stravaService.linkAccount(email, code);
        return Response.seeOther(URI.create(dashboardUrl)).build();
    }

    @GET
    @Path("/commute-candidates")
    @Operation(summary = "List Strava activities eligible for import as commute trips",
               description = "Fetches the user's recent Strava activities and keeps only those starting or ending "
                       + "within 500m of the workplace, excluding activities already imported.")
    @APIResponse(responseCode = "200", description = "Candidate activities returned")
    @APIResponse(responseCode = "400", description = "Missing X-User-Email header or work location not set")
    @APIResponse(responseCode = "404", description = "User not found")
    @APIResponse(responseCode = "409", description = "Strava account not linked")
    @APIResponse(responseCode = "502", description = "Strava API unreachable")
    public List<StravaCommuteCandidateResponse> getCommuteCandidates(@HeaderParam("X-User-Email") String email) {
        requireEmail(email);
        log.info("Listing Strava commute candidates for user: {}", email);
        return stravaService.listCommuteCandidates(email);
    }

    @POST
    @Path("/commute-candidates/import")
    @Operation(summary = "Import selected Strava activities as trips",
               description = "Creates a trip for each selected, still-eligible activity, capped at 2 imported "
                       + "trips per day. Activities beyond the cap or no longer eligible are reported as skipped.")
    @APIResponse(responseCode = "200", description = "Import processed (see imported/skipped lists)")
    @APIResponse(responseCode = "400", description = "Missing X-User-Email header, empty selection or work location not set")
    @APIResponse(responseCode = "404", description = "User not found")
    @APIResponse(responseCode = "409", description = "Strava account not linked")
    @APIResponse(responseCode = "502", description = "Strava API unreachable")
    public StravaImportResponse importCommuteCandidates(@HeaderParam("X-User-Email") String email,
                                                          @Valid StravaImportRequest request) {
        requireEmail(email);
        log.info("Importing {} Strava activities for user: {}", request.stravaActivityIds().size(), email);
        return stravaService.importActivities(email, request.stravaActivityIds());
    }

    private void requireEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new BadRequestException("Missing X-User-Email header");
        }
    }
}
