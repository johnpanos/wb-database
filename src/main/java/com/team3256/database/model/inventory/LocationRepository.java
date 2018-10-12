package com.team3256.database.model.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface LocationRepository extends PagingAndSortingRepository<Location, Long> {
    Optional<Location> findByNameIgnoringCase(String name);
    Page findAll(Pageable pageable);
}
