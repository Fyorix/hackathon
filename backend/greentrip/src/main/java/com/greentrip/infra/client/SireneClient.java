package com.greentrip.infra.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.regex.Pattern;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client for the INSEE Sirene API, used to check whether a SIREN number
 * is well-formed and corresponds to a registered company.
 */
@ApplicationScoped
public class SireneClient {

    private static final Logger log = LoggerFactory.getLogger(SireneClient.class);
    private static final Pattern SIREN_FORMAT = Pattern.compile("^\\d{9}$");

    @ConfigProperty(name = "sirene.api.base-url")
    String baseUrl;

    @ConfigProperty(name = "sirene.api.key")
    String apiKey;

    /**
     * Checks that a SIREN number is well-formed (9 digits, valid Luhn checksum)
     * and that it corresponds to a company registered in the Sirene directory.
     */
    public boolean isValidSiren(String sirenNumber) {
        if (sirenNumber == null || !SIREN_FORMAT.matcher(sirenNumber).matches()) {
            log.debug("Rejected SIREN (bad format): {}", sirenNumber);
            return false;
        }
        if (!passesLuhnChecksum(sirenNumber)) {
            log.debug("Rejected SIREN (bad checksum): {}", sirenNumber);
            return false;
        }
        return existsInSireneDirectory(sirenNumber);
    }

    private boolean existsInSireneDirectory(String sirenNumber) {
        Client client = ClientBuilder.newClient();
        try {
            Response response = client.target(baseUrl)
                    .path("siren/{siren}")
                    .resolveTemplate("siren", sirenNumber)
                    .request(MediaType.APPLICATION_JSON)
                    .header("X-INSEE-Api-Key-Integration", apiKey)
                    .get();
            try {
                int status = response.getStatus();
                if (status == 200) {
                    return true;
                }
                if (status == 404) {
                    return false;
                }
                log.warn("Unexpected Sirene API response for SIREN {}: HTTP {}", sirenNumber, status);
                return false;
            } finally {
                response.close();
            }
        } catch (ProcessingException e) {
            log.error("Failed to reach Sirene API for SIREN {}", sirenNumber, e);
            return false;
        } finally {
            client.close();
        }
    }

    /**
     * SIREN checksum follows the Luhn algorithm (same as used for SIRET/credit cards).
     */
    private boolean passesLuhnChecksum(String siren) {
        int sum = 0;
        for (int i = 0; i < siren.length(); i++) {
            int digit = siren.charAt(siren.length() - 1 - i) - '0';
            if (i % 2 == 1) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        return sum % 10 == 0;
    }
}
