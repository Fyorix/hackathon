package com.greentrip.domain.mappers;

import com.greentrip.domain.dtos.responses.TripResponse;
import com.greentrip.domain.entities.TripEntity;
import com.greentrip.domain.models.TripModel;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TripMapper {

    public TripEntity toEntity(TripModel model) {
        if (model == null) return null;
        return new TripEntity(
            model.id,
            model.distanceKm,
            model.co2Saved,
            model.pointsEarned,
            model.type,
            model.status,
            model.user != null ? model.user.id : null,
            model.createdAt
        );
    }

    public TripModel toModel(TripEntity entity) {
        if (entity == null) return null;
        TripModel model = new TripModel();
        model.id = entity.id();
        model.distanceKm = entity.distanceKm();
        model.co2Saved = entity.co2Saved();
        model.pointsEarned = entity.pointsEarned();
        model.type = entity.type();
        model.status = entity.status();
        model.createdAt = entity.createdAt();
        return model;
    }

    public TripResponse toResponse(TripEntity entity) {
        if (entity == null) return null;
        return new TripResponse(
            entity.id(),
            entity.distanceKm(),
            entity.co2Saved(),
            entity.pointsEarned(),
            entity.type(),
            entity.createdAt()
        );
    }
}
