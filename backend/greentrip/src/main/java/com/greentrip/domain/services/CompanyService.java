package com.greentrip.domain.services;

import com.greentrip.domain.dtos.requests.CreateCompanyRequest;
import com.greentrip.domain.dtos.requests.UpdateCompanyRequest;
import com.greentrip.domain.entities.CompanyEntity;
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
                .map(user -> user.company)
                .map(companyMapper::toEntity)
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
    public List<CompanyEntity> getLeaderboard(int page, int size, String sortBy, boolean descending) {
        log.info("Fetching company leaderboard (page: {}, size: {})", page, size);
        log.debug("Leaderboard sorting parameters: sortBy={}, descending={}", sortBy, descending);
        return companyRepository.findPaged(page, size, sortBy, descending).stream()
                .map(companyMapper::toEntity)
                .toList();
    }

    /**
     * Creates a new company.
     */
    @Transactional
    public CompanyEntity createCompany(CreateCompanyRequest request) {
        log.info("Creating new company: {}", request.companyName());
        if (!sireneClient.isValidSiren(request.sirenNumber())) {
            throw new WebApplicationException("Invalid or unknown SIREN number", Response.Status.BAD_REQUEST);
        }
        if (companyRepository.findByName(request.companyName()).isPresent()) {
            throw new WebApplicationException("Company name already in use", Response.Status.CONFLICT);
        }
        if (companyRepository.findBySirenNumber(request.sirenNumber()).isPresent()) {
            throw new WebApplicationException("SIREN number already in use", Response.Status.CONFLICT);
        }
        CompanyModel model = companyMapper.toModel(companyMapper.toEntity(request));
        companyRepository.persist(model);
        return companyMapper.toEntity(model);
    }

    /**
     * Updates an existing company.
     */
    @Transactional
    public CompanyEntity updateCompany(Long id, UpdateCompanyRequest request) {
        log.info("Updating company id: {}", id);
        CompanyModel model = companyRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Company not found", Response.Status.NOT_FOUND));

        Optional<CompanyModel> existing = companyRepository.findByName(request.companyName());
        if (existing.isPresent() && !existing.get().id.equals(id)) {
            throw new WebApplicationException("Company name already in use", Response.Status.CONFLICT);
        }

        model.name = request.companyName();
        if (request.logoPath() != null) {
            model.logoPath = request.logoPath();
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

}
