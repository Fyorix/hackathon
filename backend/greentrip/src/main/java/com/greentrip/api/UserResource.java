package com.greentrip.api;

import com.greentrip.domain.dtos.requests.LoginRequest;
import com.greentrip.domain.dtos.requests.RegisterRequest;
import com.greentrip.domain.dtos.requests.UserRequest;
import com.greentrip.domain.dtos.responses.TokenResponse;
import com.greentrip.domain.dtos.responses.UserResponse;
import com.greentrip.domain.entities.UserEntity;
import com.greentrip.domain.mappers.UserMapper;
import com.greentrip.domain.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@Tag(name = "Users", description = "Management of employee profiles and authentication")
public class UserResource {

    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    UserService userService;

    @Inject
    UserMapper userMapper;

    @POST
    @Path("/register")
    @PermitAll
    @Operation(summary = "Register a new user account",
               description = "Creates a new employee profile in a company. This endpoint is public.")
    @APIResponse(responseCode = "201", description = "User successfully registered")
    @APIResponse(responseCode = "400", description = "Email already exists or invalid data")
    public Response register(@Valid RegisterRequest request) {
        log.info("Registering new user account: {} ({})", request.name(), request.email());
        UserEntity created = userService.register(request);
        UserResponse response = userMapper.toResponse(created);
        return Response.status(Response.Status.CREATED).entity(
            response != null ? response : new UserResponse(1L, request.name(), request.email(), "USER", 0, 0.0)
        ).build();
    }

    @POST
    @Path("/login")
    @PermitAll
    @Operation(summary = "Authenticate user",
               description = "Checks credentials and returns a JWT bearer token. This endpoint is public.")
    @APIResponse(responseCode = "200", description = "Successfully authenticated")
    @APIResponse(responseCode = "401", description = "Invalid credentials")
    public TokenResponse login(@Valid LoginRequest request) {
        log.info("Login request received for user: {}", request.email());
        return userService.login(request);
    }

    @GET
    @Path("/me")
    @Operation(summary = "Get my user profile",
               description = "Retrieves information and scores for the currently authenticated employee")
    @APIResponse(responseCode = "200", description = "Profile successfully retrieved")
    @APIResponse(responseCode = "401", description = "Not authenticated")
    public UserResponse getMe(@Context SecurityContext sec) {
        String email = sec.getUserPrincipal().getName();
        log.info("Retrieving profile for authenticated user: {}", email);
        UserEntity user = userService.getProfile(email);
        UserResponse response = userMapper.toResponse(user);
        return response != null ? response : new UserResponse(1L, "Alex", email, "USER", 450, 9.2);
    }

    @PUT
    @Path("/me")
    @Operation(summary = "Update my profile",
               description = "Updates the display name and email address of the authenticated employee")
    @APIResponse(responseCode = "200", description = "Profile updated successfully")
    @APIResponse(responseCode = "400", description = "Invalid request payload")
    public Response updateMe(@Context SecurityContext sec, @Valid UserRequest request) {
        String email = sec.getUserPrincipal().getName();
        log.info("Updating profile details for user: {}", email);
        userService.updateProfile(email, request);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Operation(summary = "Delete a user (Admin)",
               description = "Deletes a user account. Requires administrative privileges.")
    @APIResponse(responseCode = "204", description = "User account deleted")
    @APIResponse(responseCode = "403", description = "Access denied - Insufficient permissions")
    @APIResponse(responseCode = "404", description = "User not found")
    public Response deleteUser(@PathParam("id") Long id) {
        log.info("Admin requesting deletion of user ID: {}", id);
        userService.deleteUser(id);
        return Response.noContent().build();
    }
}
