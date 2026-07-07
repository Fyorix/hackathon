package com.greentrip.api;

import com.greentrip.domain.dtos.requests.TripRequest;
import com.greentrip.domain.dtos.responses.TripResponse;
import com.greentrip.domain.entities.TripEntity;
import com.greentrip.domain.mappers.TripMapper;
import com.greentrip.domain.services.TripService;
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
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/trips")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@Tag(name = "Trips", description = "Management of green trips")
public class TripResource {

    private static final Logger log = LoggerFactory.getLogger(TripResource.class);

    @Inject
    TripService tripService;

    @Inject
    TripMapper tripMapper;

    @GET
    @Operation(summary = "Get paged trip history",
               description = "Retrieves paged trip declarations of the currently authenticated employee with dynamic sorting")
    @APIResponse(responseCode = "200", description = "History successfully retrieved")
    @APIResponse(responseCode = "401", description = "Not authenticated")
    public List<TripResponse> getTrips(
            @Context SecurityContext sec,
            @Parameter(description = "Page index", example = "0") 
            @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Page size", example = "10") 
            @QueryParam("size") @DefaultValue("10") int size,
            @Parameter(description = "Sort field", example = "createdAt") 
            @QueryParam("sortBy") @DefaultValue("createdAt") String sortBy,
            @Parameter(description = "Sort descending if true", example = "true") 
            @QueryParam("desc") @DefaultValue("true") boolean desc) {
        
        String email = sec.getUserPrincipal().getName();
        log.info("Fetching trips for user: {} (page: {}, size: {}, sortBy: {}, desc: {})", email, page, size, sortBy, desc);
        List<TripEntity> trips = tripService.getTrips(email, page, size, sortBy, desc);
        return trips.stream().map(tripMapper::toResponse).toList();
    }

    @POST
    @Operation(summary = "Declare a new trip",
               description = "Registers a trip, calculates CO2 savings and credits user points automatically")
    @APIResponse(responseCode = "201", description = "Trip successfully registered")
    @APIResponse(responseCode = "400", description = "Invalid request payload")
    public Response createTrip(@Context SecurityContext sec, @Valid TripRequest request) {
        String email = sec.getUserPrincipal().getName();
        log.info("User {} declaring a new trip of {} km using {}", email, request.distanceKm(), request.type());
        TripEntity created = tripService.declareTrip(email, request);
        TripResponse response = tripMapper.toResponse(created);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a trip declaration", description = "Removes a trip declaration from history")
    @APIResponse(responseCode = "204", description = "Trip successfully deleted")
    @APIResponse(responseCode = "404", description = "Trip not found")
    public Response deleteTrip(@Context SecurityContext sec, @PathParam("id") Long id) {
        String email = sec.getUserPrincipal().getName();
        log.info("User {} requesting deletion of trip ID: {}", email, id);
        tripService.deleteTrip(email, id);
        return Response.noContent().build();
    }
}
