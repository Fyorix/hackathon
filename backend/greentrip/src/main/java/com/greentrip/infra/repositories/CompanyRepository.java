package com.greentrip.infra.repositories;

import com.greentrip.domain.models.CompanyModel;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CompanyRepository implements BaseRepository<CompanyModel> {
    private static final Logger log = LoggerFactory.getLogger(CompanyRepository.class);

    /**
     * Finds a company by its name.
     * @param name The company's name
     * @return An optional containing the company if found
     */
    public Optional<CompanyModel> findByName(String name) {
        log.debug("Database query: Fetching company by name: {}", name);
        return find("name", name).firstResultOptional();
    }

    /**
     * Finds a company by its SIREN number.
     * @param sirenNumber The company's SIREN number
     * @return An optional containing the company if found
     */
    public Optional<CompanyModel> findBySirenNumber(String sirenNumber) {
        log.debug("Database query: Fetching company by SIREN number: {}", sirenNumber);
        return find("sirenNumber", sirenNumber).firstResultOptional();
    }
}
