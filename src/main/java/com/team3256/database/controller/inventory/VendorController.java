package com.team3256.database.controller.inventory;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.inventory.Vendor;
import com.team3256.database.model.inventory.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory/vendor")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_EDIT" })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Vendor createVendor(@RequestBody @Valid Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_EDIT" })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Vendor updateVendor(@RequestBody Vendor vendor) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(vendor.getId());

        if (!vendorOptional.isPresent()) {
            throw new DatabaseNotFoundException();
        }

        Vendor dbVendor = vendorOptional.get();

        dbVendor.setName(vendor.getName());
        dbVendor.setAddress(vendor.getAddress());
        dbVendor.setEmail(vendor.getEmail());
        dbVendor.setPhoneNumber(vendor.getPhoneNumber());
        dbVendor.setWebsite(vendor.getWebsite());

        return vendorRepository.save(dbVendor);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_EDIT" })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Integer deleteVendor(@PathVariable("id") Integer id) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(id);

        if (!vendorOptional.isPresent()) {
            throw new DatabaseNotFoundException();
        }

        vendorRepository.deleteById(id);

        return id;
    }
}
