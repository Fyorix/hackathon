package com.greentrip.domain.services;

import com.greentrip.domain.dtos.requests.CreateCompanyRequest;
import com.greentrip.domain.dtos.requests.UpdateCompanyRequest;
import com.greentrip.domain.entities.CompanyEntity;
import com.greentrip.domain.entities.CompanySortField;
import com.greentrip.domain.entities.UserEntity;
import com.greentrip.domain.entities.UserSortField;
import com.greentrip.domain.mappers.CompanyMapper;
import com.greentrip.domain.models.CompanyModel;
import com.greentrip.infra.client.SireneClient;
import com.greentrip.infra.repositories.CompanyRepository;
import com.greentrip.infra.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CompanyService {

    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);

    @Inject
    CompanyRepository companyRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CompanyMapper companyMapper;

    @Inject
    SireneClient sireneClient;

    /**
     * Gets RSE statistics for the connected user's company.
     */
    public CompanyEntity getCompanyDetails(String email) {
        log.info("Fetching company statistics for user email: {}", email);
        return userRepository.findByEmail(email)
                .map(UserEntity::companyId)
                .flatMap(companyRepository::findEntityById)
                .orElse(null);
    }

    /**
     * Gets a company by its id.
     */
    public CompanyEntity getCompanyById(Long id) {
        log.info("Fetching company by id: {}", id);
        return companyRepository.findByIdOptional(id)
                .map(companyMapper::toEntity)
                .orElseThrow(() -> new WebApplicationException("Company not found", Response.Status.NOT_FOUND));
    }

    /**
     * Gets the global leaderboard of companies.
     */
    public List<CompanyEntity> getLeaderboard(int page, int size, CompanySortField sortBy, boolean descending) {
        log.info("Fetching company leaderboard (page: {}, size: {})", page, size);
        log.debug("Leaderboard sorting parameters: sortBy={}, descending={}", sortBy, descending);
        return companyRepository.findPaged(page, size, sortBy.getFieldName(), descending).stream()
                .map(companyMapper::toEntity)
                .toList();
    }

    /**
     * Creates a new company.
     */
    @Transactional
    public CompanyEntity createCompany(CreateCompanyRequest request) {
        log.info("Creating new company: {}", request.companyName());
//        if (!sireneClient.isValidSiren(request.sirenNumber())) {
//            throw new WebApplicationException("Invalid or unknown SIREN number", Response.Status.BAD_REQUEST);
//        }
        if (companyRepository.findByName(request.companyName()).isPresent()) {
            throw new WebApplicationException("Company name already in use", Response.Status.CONFLICT);
        }
        if (companyRepository.findBySirenNumber(request.sirenNumber()).isPresent()) {
            throw new WebApplicationException("SIREN number already in use", Response.Status.CONFLICT);
        }
        CompanyEntity companyEntity = companyMapper.toEntity(request);
        return companyRepository.create(companyEntity);
    }

    /**
     * Updates an existing company.
     */
    @Transactional
    public CompanyEntity updateCompany(Long id, UpdateCompanyRequest request) {
        log.info("Updating company id: {}", id);
        CompanyModel model = companyRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Company not found", Response.Status.NOT_FOUND));

        Optional<CompanyEntity> existing = companyRepository.findByName(request.companyName());
        if (existing.isPresent() && !existing.get().id().equals(id)) {
            throw new WebApplicationException("Company name already in use", Response.Status.CONFLICT);
        }

        model.name = request.companyName();
        if (request.logoPath() != null) {
            model.logoPath = request.logoPath();
        }
        if (request.latitude() != null) {
            model.latitude = request.latitude();
        }
        if (request.longitude() != null) {
            model.longitude = request.longitude();
        }
        return companyMapper.toEntity(model);
    }

    /**
     * Deletes a company.
     */
    @Transactional
    public void deleteCompany(Long id) {
        log.info("Deleting company id: {}", id);
        boolean deleted = companyRepository.deleteById(id);
        if (!deleted) {
            throw new WebApplicationException("Company not found", Response.Status.NOT_FOUND);
        }
    }

    /**
     * Gets sorted leaderboard of users belonging to a company.
     */
    public List<UserEntity> getCompanyUserLeaderboard(Long companyId, int page, int size, UserSortField sortBy, boolean descending) {
        log.info("Getting leaderboard of users for company id: {}", companyId);
        // Verify if company exists
        companyRepository.findByIdOptional(companyId)
                .orElseThrow(() -> new WebApplicationException("Company not found", Response.Status.NOT_FOUND));
        
        return userRepository.findPagedLeaderboardByCompany(companyId, page, size, sortBy, descending);
    }

    /**
     * Searches companies by name with pagination, ensuring query is at least 4 characters.
     */
    public List<CompanyEntity> searchCompanies(String query, int page, int size) {
        log.info("Searching companies with query: '{}' (page: {}, size: {})", query, page, size);
        if (query == null || query.trim().length() < 4) {
            log.warn("Search query too short: '{}'", query);
            throw new WebApplicationException("Search query must be at least 4 characters long", Response.Status.BAD_REQUEST);
        }
        return companyRepository.findPagedByNameQuery(query.trim(), page, size);
    }

    /**
     * Searches users within a company leaderboard by name with pagination, ensuring query is at least 4 characters.
     */
    public List<UserEntity> searchCompanyUsers(Long companyId, String query, int page, int size) {
        log.info("Searching users in company ID {} with query: '{}' (page: {}, size: {})", companyId, query, page, size);
        // Verify if company exists
        companyRepository.findByIdOptional(companyId)
                .orElseThrow(() -> new WebApplicationException("Company not found", Response.Status.NOT_FOUND));

        if (query == null || query.trim().length() < 4) {
            log.warn("User search query too short: '{}'", query);
            throw new WebApplicationException("Search query must be at least 4 characters long", Response.Status.BAD_REQUEST);
        }
        return userRepository.findPagedUsersByNameQueryAndCompany(companyId, query.trim(), page, size);
    }

}
