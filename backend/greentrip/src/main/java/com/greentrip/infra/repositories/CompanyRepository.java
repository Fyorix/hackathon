package com.greentrip.infra.repositories;

import com.greentrip.domain.entities.CompanyEntity;
import com.greentrip.domain.models.CompanyModel;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CompanyRepository extends AbstractBaseRepository<CompanyModel, CompanyEntity> {

    private static final Logger log = LoggerFactory.getLogger(CompanyRepository.class);

    /**
     * Finds a company by its name.
     * @param name The company's name
     * @return An optional containing the company if found
     */
    public Optional<CompanyEntity> findByName(String name) {
        log.debug("Database query: Fetching company by name: {}", name);
        return findEntityByField("name", name);
    }

    /**
     * Finds a company by its SIREN number.
     * @param sirenNumber The company's SIREN number
     * @return An optional containing the company if found
     */
    public Optional<CompanyEntity> findBySirenNumber(String sirenNumber) {
        log.debug("Database query: Fetching company by SIREN number: {}", sirenNumber);
        return findEntityByField("sirenNumber", sirenNumber);
    }

    /**
     * Finds companies whose name contains the query, case-insensitive, with pagination.
     */
    public java.util.List<CompanyEntity> findPagedByNameQuery(String query, int page, int size) {
        log.debug("Database query: Searching companies matching '{}' (page={}, size={})", query, page, size);
        return find("lower(name) like lower(?1)", "%" + query + "%")
                .page(page, size)
                .list()
                .stream()
                .map(mapper::toEntity)
                .toList();
    }
}
