package com.team3256.database.model.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PartRepository extends PagingAndSortingRepository<Part, Integer> {
    Page findAll(Pageable pageable);

    Page findByNameIgnoreCaseContaining(String search, Pageable pageable);

    @Query("SELECT count(*) FROM part WHERE quantity <= min_quantity")
    Long getLowCount();
}
