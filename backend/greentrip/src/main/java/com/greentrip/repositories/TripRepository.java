package com.greentrip.repositories;

import com.greentrip.models.TripModel;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TripRepository implements BaseRepository<TripModel> {

    private static final Logger log = LoggerFactory.getLogger(TripRepository.class);

    /**
     * Retrieves paged trips for a specific user with dynamic sorting.
     */
    public List<TripModel> findPagedByUser(Long userId, int page, int size, String sortBy, boolean descending) {
        log.debug("Database query: Fetching trips for user ID: {} (page: {}, size: {}, sortBy: {}, desc: {})", userId, page, size, sortBy, descending);
        Sort sort = descending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return find("user.id", sort, userId)
                .page(Page.of(page, size))
                .list();
    }
}
