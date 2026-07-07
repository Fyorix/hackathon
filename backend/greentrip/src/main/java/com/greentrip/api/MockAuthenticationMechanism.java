package com.greentrip.api;

import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MockAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final Logger log = LoggerFactory.getLogger(MockAuthenticationMechanism.class);

    // List of pre-seeded ADMIN emails from import.sql.
    // Since registration only allows creating "USER" role accounts,
    // this stateless set is 100% complete and avoids any database or CDI context issues during Vert.x boot.
    private static final Set<String> ADMIN_EMAILS = Set.of(
        "antoine@takima.fr",
        "charles.fournier122@decathlon.fr",
        "nicolas.roussel140@alan.eu",
        "chloe.petit152@doctolib.fr",
        "sophie.thomas167@backmarket.com",
        "eva.guerin181@blablacar.com",
        "zoe.fontaine197@swile.co",
        "marie.dubois208@payfit.com",
        "alexandre.robert221@yuka.io",
        "eva.robert229@lvmh.fr",
        "julien.martin236@sncf.fr",
        "sophie.fontaine261@edf.fr",
        "julie.guerin282@mirakl.com",
        "sophie.henry292@contentsquare.com",
        "pierre.moreau306@manomano.fr",
        "laura.dupont318@openclassrooms.com",
        "charles.guerin327@evaneos.com",
        "sarah.fontaine338@veepee.com",
        "ines.fontaine351@luko.eu",
        "laura.muller357@yousign.com"
    );

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        String authHeader = context.request().getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer mock-jwt-token-for-")) {
            String email = authHeader.substring("Bearer mock-jwt-token-for-".length()).trim();
            
            String role = ADMIN_EMAILS.contains(email.toLowerCase()) ? "ADMIN" : "USER";
            
            SecurityIdentity identity = QuarkusSecurityIdentity.builder()
                    .setPrincipal(() -> email)
                    .addRole(role)
                    .build();
            
            log.info("Mock authenticated user {} with role {} via HTTP mechanism (stateless)", email, role);
            return Uni.createFrom().item(identity);
        }
        
        return Uni.createFrom().nullItem();
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        ChallengeData challengeData = new ChallengeData(401, "WWW-Authenticate", "Bearer");
        return Uni.createFrom().item(challengeData);
    }
}
