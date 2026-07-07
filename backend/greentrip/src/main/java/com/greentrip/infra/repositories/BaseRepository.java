package com.greentrip.infra.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import java.util.List;

public interface BaseRepository<Entity> extends PanacheRepositoryBase<Entity, Long> {
    
    /**
     * Retrieves a page of data sorted dynamically.
     * @param page Page index (0-based)
     * @param size Page size
     * @param sortBy Field name to sort by (e.g. "createdAt" or "name")
     * @param descending True to sort in descending order, False for ascending
     * @return List of paged entities
     */
    default List<Entity> findPaged(int page, int size, String sortBy, boolean descending) {
        Sort sort = descending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return findAll(sort)
                .page(Page.of(page, size))
                .list();
    }
}
