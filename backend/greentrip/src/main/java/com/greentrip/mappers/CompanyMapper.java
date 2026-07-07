package com.greentrip.mappers;

import com.greentrip.dtos.responses.CompanyResponse;
import com.greentrip.entities.CompanyEntity;
import com.greentrip.models.CompanyModel;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompanyMapper {

    public CompanyEntity toEntity(CompanyModel model) {
        if (model == null) return null;
        return new CompanyEntity(
            model.id,
            model.name,
            model.totalEmployees,
            model.totalCo2Saved,
            model.unlockedBadge,
            model.createdAt
        );
    }

    public CompanyModel toModel(CompanyEntity entity) {
        if (entity == null) return null;
        CompanyModel model = new CompanyModel();
        model.id = entity.id();
        model.name = entity.name();
        model.totalEmployees = entity.totalEmployees();
        model.totalCo2Saved = entity.totalCo2Saved();
        model.unlockedBadge = entity.unlockedBadge();
        model.createdAt = entity.createdAt();
        return model;
    }

    public CompanyResponse toResponse(CompanyEntity entity) {
        if (entity == null) return null;
        return new CompanyResponse(
            entity.id(),
            entity.name(),
            entity.totalEmployees(),
            entity.totalCo2Saved(),
            entity.unlockedBadge()
        );
    }
}
