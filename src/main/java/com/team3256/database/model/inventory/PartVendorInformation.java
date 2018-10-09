package com.team3256.database.model.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class PartVendorInformation {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String partNumber;

    @NotNull
    @OneToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "part_id")
    @JsonBackReference
    private Part part;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
