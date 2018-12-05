package com.team3256.database.model.inventory.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    Page findAll(Pageable pageable);
    Page findAllByOrderByNeedByDateAsc(Pageable pageable);
    Page findAllByOrderByNeedByDateDesc(Pageable pageable);
}
