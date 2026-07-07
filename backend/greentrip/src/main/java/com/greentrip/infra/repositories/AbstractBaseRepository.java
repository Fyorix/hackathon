package com.greentrip.infra.repositories;

import com.greentrip.domain.mappers.GenericMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBaseRepository<Model, Entity> implements PanacheRepositoryBase<Model, Long> {

    @Inject
    protected GenericMapper<Model, Entity> mapper;

    @Inject
    protected EntityManager entityManager;

    /**
     * Retrieves a page of data sorted dynamically.
     */
    public List<Model> findPaged(int page, int size, String sortBy, boolean descending) {
        Sort sort = descending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return findAll(sort)
                .page(Page.of(page, size))
                .list();
    }

    /**
     * Finds an entity by its ID and maps it to the domain Entity.
     */
    public Optional<Entity> findEntityById(Long id) {
        Model model = findById(id);
        return Optional.ofNullable(mapper.toEntity(model));
    }

    /**
     * Persists a new Entity in the database.
     */
    public Entity create(Entity entity) {
        Model model = mapper.toModel(entity);
        persist(model);
        return mapper.toEntity(model);
    }

    /**
     * Updates an existing Entity in the database.
     */
    public Entity update(Entity entity) {
        Model model = mapper.toModel(entity);
        model = entityManager.merge(model);
        return mapper.toEntity(model);
    }

    /**
     * Deletes an entity by its ID.
     */
    public boolean deleteEntityById(Long id) {
        return deleteById(id);
    }
}
