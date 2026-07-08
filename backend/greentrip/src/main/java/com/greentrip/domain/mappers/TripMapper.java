package com.greentrip.domain.mappers;

import com.greentrip.domain.dtos.responses.TripResponse;
import com.greentrip.domain.entities.TripEntity;
import com.greentrip.domain.models.TripModel;
import com.greentrip.domain.models.UserModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class TripMapper implements GenericMapper<TripModel, TripEntity> {

    @Inject
    EntityManager entityManager;

    @Override
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
            model.createdAt,
            model.stravaActivityId,
            model.tripDate
        );
    }

    @Override
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
        model.stravaActivityId = entity.stravaActivityId();
        model.tripDate = entity.tripDate();

        if (entity.userId() != null) {
            model.user = entityManager.find(UserModel.class, entity.userId());
        }
        
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
