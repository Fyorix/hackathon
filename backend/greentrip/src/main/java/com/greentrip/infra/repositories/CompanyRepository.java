package com.greentrip.infra.repositories;

import com.greentrip.domain.entities.CompanyEntity;
import com.greentrip.domain.models.CompanyModel;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CompanyRepository extends AbstractBaseRepository<CompanyModel, CompanyEntity> {

    private static final Logger log = LoggerFactory.getLogger(CompanyRepository.class);
}
