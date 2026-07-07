package com.greentrip.domain.mappers;

import com.greentrip.domain.dtos.responses.UserResponse;
import com.greentrip.domain.entities.UserEntity;
import com.greentrip.domain.models.UserModel;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {

    public UserEntity toEntity(UserModel model) {
        if (model == null) return null;
        return new UserEntity(
            model.id,
            model.name,
            model.email,
            model.role,
            model.carbonPointsBalance,
            model.totalCo2Saved,
            model.company != null ? model.company.id : null,
            model.createdAt
        );
    }

    public UserModel toModel(UserEntity entity) {
        if (entity == null) return null;
        UserModel model = new UserModel();
        model.id = entity.id();
        model.name = entity.name();
        model.email = entity.email();
        model.role = entity.role();
        model.carbonPointsBalance = entity.carbonPointsBalance();
        model.totalCo2Saved = entity.totalCo2Saved();
        model.createdAt = entity.createdAt();
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
            entity.totalCo2Saved()
        );
    }
}
