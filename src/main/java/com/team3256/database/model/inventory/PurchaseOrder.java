package com.team3256.database.model.inventory;

import com.team3256.database.model.hr.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

public class PurchaseOrder {
    @Column(name = "part")
    private Part part;

    @Column(name = "vendor_information")
    private PartVendorInformation vendorInformation;

    @Column(name = "user")
    private User orderedBy;

    @Column(name = "ordered_on_date")
    private Date orderedOnDate;

    @Column(name = "need_by_date")
    private Date needByDate;

    @Column(name = "requested_quantity")
    private String requestedQuantity;

    @Column(name = "cost_per_item")
    private Integer costPerItem;

    @Column(name = "total_cost")
    private Integer totalCost;

    @Column(name = "ordering_department")
    private String orderingDepartment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus status;
}
