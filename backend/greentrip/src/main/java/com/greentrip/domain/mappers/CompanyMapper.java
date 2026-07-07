package com.greentrip.domain.mappers;

import com.greentrip.domain.dtos.requests.CreateCompanyRequest;
import com.greentrip.domain.dtos.responses.CompanyResponse;
import com.greentrip.domain.entities.CompanyEntity;
import com.greentrip.domain.models.CompanyModel;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompanyMapper implements GenericMapper<CompanyModel, CompanyEntity> {

    @Override
    public CompanyEntity toEntity(CompanyModel model) {
        if (model == null) return null;
        return new CompanyEntity(
                model.id,
                model.name,
                model.sirenNumber,
                model.totalEmployees,
                model.totalCo2Saved,
                model.totalPoints,
                model.totalKm,
                model.createdAt,
                model.latitude,
                model.longitude,
                model.logoPath
        );
    }

    @Override
    public CompanyModel toModel(CompanyEntity entity) {
        if (entity == null) return null;
        CompanyModel model = new CompanyModel();
        model.id = entity.id();
        model.name = entity.name();
        model.sirenNumber = entity.sirenNumber();
        model.totalEmployees = entity.totalEmployees();
        model.totalCo2Saved = entity.totalCo2Saved();
        model.totalPoints = entity.totalPoints();
        model.totalKm = entity.totalKm();
        if (entity.createdAt() != null) {
            model.createdAt = entity.createdAt();
        }
        model.latitude = entity.latitude();
        model.longitude = entity.longitude();
        model.logoPath = entity.logoPath();
        return model;
    }

    public CompanyEntity toEntity(CreateCompanyRequest request) {
        if (request == null) return null;
        return new CompanyEntity(
                null,
                request.companyName(),
                request.sirenNumber(),
                0,
                0.0,
                0,
                0.0,
                null,
                request.latitude(),
                request.longitude(),
                request.logoPath() != null ? request.logoPath() : "default.png"
        );
    }

    public CompanyResponse toResponse(CompanyEntity entity) {
        if (entity == null) return null;
        return new CompanyResponse(
                entity.id(),
                entity.name(),
                entity.sirenNumber(),
                entity.totalEmployees(),
                entity.totalCo2Saved(),
                entity.totalPoints(),
                entity.totalKm(),
                entity.latitude(),
                entity.longitude(),
                entity.logoPath()
        );
    }
}
