package com.team3256.database.model.inventory;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "part")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(max = 64)
    private String name;

    @NotEmpty
    @Size(max = 64)
    private String nomenclature;

    @Min(value = 0, message = "Quantity must be more than 0")
    private int quantity;

    @Min(value = 0, message = "Minimum quantity must be more than 0")
    private int minQuantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @NotEmpty(message = "Please fill in a sublocation")
    @Size(max = 64, message = "Sublocation must me no longer than 64 characters")
    private String sublocation;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "part_id")
    private List<PartVendorInformation> vendorInformation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSublocation() {
        return sublocation;
    }

    public void setSublocation(String sublocation) {
        this.sublocation = sublocation;
    }

    public List<PartVendorInformation> getVendorInformation() {
        return vendorInformation;
    }

    public void setVendorInformation(List<PartVendorInformation> vendorInformation) {
        this.vendorInformation = vendorInformation;
    }
}
