package com.greentrip.infra.repositories;

import com.greentrip.domain.entities.TripEntity;
import com.greentrip.domain.models.TripModel;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TripRepository extends AbstractBaseRepository<TripModel, TripEntity> {

    private static final Logger log = LoggerFactory.getLogger(TripRepository.class);
}
