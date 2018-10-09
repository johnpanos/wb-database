package com.team3256.database.model.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PartRepository extends PagingAndSortingRepository<Part, Long> {
    Page findAll(Pageable pageable);

    List<Part> findByNameIgnoreCaseContaining(String search);
}
