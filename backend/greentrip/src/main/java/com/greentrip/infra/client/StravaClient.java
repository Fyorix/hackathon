package com.greentrip.infra.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client for the Strava OAuth2 API, used to link a user account to Strava
 * and exchange the authorization code for a refresh/access token pair.
 */
@ApplicationScoped
public class StravaClient {

    private static final Logger log = LoggerFactory.getLogger(StravaClient.class);

    private static final String AUTHORIZE_URL = "https://www.strava.com/oauth/authorize";
    private static final String TOKEN_URL = "https://www.strava.com/oauth/token";
    private static final String SCOPES = "read,activity:read_all";

    @ConfigProperty(name = "strava.client-id")
    String clientId;

    @ConfigProperty(name = "strava.client-secret")
    String clientSecret;

    @ConfigProperty(name = "strava.redirect-uri")
    String redirectUri;

    /**
     * Builds the URL the frontend must redirect the user to in order to grant
     * GreenTrip access to their Strava account.
     */
    public String buildAuthorizationUrl(String state) {
        return UriBuilder.fromUri(AUTHORIZE_URL)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("approval_prompt", "auto")
                .queryParam("scope", SCOPES)
                .queryParam("state", state)
                .build()
                .toString();
    }

    /**
     * Exchanges the authorization code received from Strava for an access/refresh token pair.
     */
    public StravaTokenResponse exchangeAuthorizationCode(String code) {
        Client client = ClientBuilder.newClient();
        try {
            Form form = new Form()
                    .param("client_id", clientId)
                    .param("client_secret", clientSecret)
                    .param("code", code)
                    .param("grant_type", "authorization_code");

            Response response = client.target(TOKEN_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.form(form));
            try {
                int status = response.getStatus();
                if (status != 200) {
                    log.error("Strava token exchange failed: HTTP {}", status);
                    throw new WebApplicationException("Failed to exchange Strava authorization code", 502);
                }
                return response.readEntity(StravaTokenResponse.class);
            } finally {
                response.close();
            }
        } catch (ProcessingException e) {
            log.error("Failed to reach Strava API for token exchange", e);
            throw new WebApplicationException("Unable to reach Strava API", 502);
        } finally {
            client.close();
        }
    }
}
