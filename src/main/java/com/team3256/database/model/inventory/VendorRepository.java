package com.team3256.database.model.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VendorRepository extends PagingAndSortingRepository<Vendor, Long> {
    Page findAll(Pageable pageable);
}
