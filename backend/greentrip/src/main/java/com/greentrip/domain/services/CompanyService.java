package com.greentrip.domain.services;

import com.greentrip.domain.entities.CompanyEntity;
import com.greentrip.domain.mappers.CompanyMapper;
import com.greentrip.infra.repositories.CompanyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CompanyService {

    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);

    @Inject
    CompanyRepository companyRepository;

    @Inject
    CompanyMapper companyMapper;

    /**
     * Gets RSE statistics for the connected user's company.
     */
    public CompanyEntity getCompanyDetails(String email) {
        log.info("Fetching company statistics for user email: {}", email);
        // TODO: implement business logic to fetch company statistics
        return null;
    }

    /**
     * Gets the global leaderboard of companies.
     */
    public List<CompanyEntity> getLeaderboard(int page, int size, String sortBy, boolean descending) {
        log.info("Fetching company leaderboard (page: {}, size: {})", page, size);
        log.debug("Leaderboard sorting parameters: sortBy={}, descending={}", sortBy, descending);
        // TODO: implement business logic to retrieve company leaderboard
        return List.of();
    }
}
