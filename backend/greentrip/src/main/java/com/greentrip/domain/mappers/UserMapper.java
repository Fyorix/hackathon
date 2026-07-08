package com.greentrip.domain.mappers;

import com.greentrip.domain.dtos.responses.UserResponse;
import com.greentrip.domain.entities.UserEntity;
import com.greentrip.domain.models.CompanyModel;
import com.greentrip.domain.models.UserModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class UserMapper implements GenericMapper<UserModel, UserEntity> {

    @Inject
    EntityManager entityManager;

    @Override
    public UserEntity toEntity(UserModel model) {
        if (model == null) return null;
        return new UserEntity(
            model.id,
            model.name,
            model.email,
            model.password,
            model.role,
            model.carbonPointsBalance,
            model.totalCo2Saved,
            model.totalKm,
            model.workLat,
            model.workLng,
            model.workStartTime,
            model.workEndTime,
            model.company != null ? model.company.id : null,
            model.createdAt,
            model.stravaAthleteId,
            model.stravaAccessToken,
            model.stravaRefreshToken,
            model.stravaTokenExpiresAt
        );
    }

    @Override
    public UserModel toModel(UserEntity entity) {
        if (entity == null) return null;
        UserModel model = null;
        if (entity.id() != null) {
            model = entityManager.find(UserModel.class, entity.id());
        }
        if (model == null) {
            model = new UserModel();
            model.id = entity.id();
        }
        model.name = entity.name();
        model.email = entity.email();
        model.password = entity.password();
        model.role = entity.role();
        model.carbonPointsBalance = entity.carbonPointsBalance();
        model.totalCo2Saved = entity.totalCo2Saved();
        model.totalKm = entity.totalKm();
        model.stravaRefreshToken = entity.stravaRefreshToken();
        model.workLat = entity.workLat();
        model.workLng = entity.workLng();
        model.workStartTime = entity.workStartTime();
        model.workEndTime = entity.workEndTime();
        model.stravaAthleteId = entity.stravaAthleteId();
        model.stravaAccessToken = entity.stravaAccessToken();
        model.stravaTokenExpiresAt = entity.stravaTokenExpiresAt();
        model.createdAt = entity.createdAt();
        if (entity.companyId() != null) {
            model.company = entityManager.find(CompanyModel.class, entity.companyId());
        } else {
            model.company = null;
        }
        return model;
    }

    public UserResponse toResponse(UserEntity entity) {
        if (entity == null) return null;
        return new UserResponse(
            entity.id(),
            entity.name(),
            entity.email(),
            entity.role(),
            entity.carbonPointsBalance(),
            entity.totalCo2Saved(),
            entity.totalKm()
        );
    }
}
