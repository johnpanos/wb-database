package com.team3256.database.controller.inventory;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.inventory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/inventory/part/vendor-info")
public class VendorInformationController {
    @Autowired
    private PartRepository partRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private PartVendorInformationRepository partVendorInformationRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Part addPartVendorInformation(@PathVariable("id") Integer id, @RequestBody PartVendorInformation partVendorInformation, @RequestParam("vendor") Integer vendorId) {
        Optional<Part> partOptional = partRepository.findById(id);
        Optional<Vendor> vendorOptional = vendorRepository.findById(vendorId);

        if (!partOptional.isPresent()) {
            throw new DatabaseNotFoundException();
        }

        if (!vendorOptional.isPresent()) {
            throw new DatabaseNotFoundException();
        }

        Part part = partOptional.get();
        Vendor vendor = vendorOptional.get();
        partVendorInformation.setVendor(vendor);
        partVendorInformation.setPart(part);
        partVendorInformationRepository.save(partVendorInformation);
        part.getVendorInformation().add(partVendorInformation);
        partRepository.save(part);
        return part;
    }

    @Secured("ROLE_USER")
    @PutMapping("/{id}")
    public PartVendorInformation updatePartVendorInformation(@PathVariable Integer id, @RequestBody PartVendorInformation partVendorInformation) {
        Optional<PartVendorInformation> partVendorInformationOptional = partVendorInformationRepository.findById(id);
        Optional<Vendor> vendorOptional = vendorRepository.findById(partVendorInformation.getVendor().getId());

        if (!partVendorInformationOptional.isPresent()) {
            throw new DatabaseNotFoundException();
        }

        if (!vendorOptional.isPresent()) {
            throw new DatabaseNotFoundException();
        }

        PartVendorInformation dbPartVendorInformation = partVendorInformationOptional.get();
        Vendor dbVendor = vendorOptional.get();

        dbPartVendorInformation.setVendor(dbVendor);
        dbPartVendorInformation.setPartNumber(partVendorInformation.getPartNumber());

        return partVendorInformationRepository.save(dbPartVendorInformation);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{id}")
    public Integer deletePartVendorInformation(@PathVariable Integer id) {
        Optional<PartVendorInformation> partVendorInformationOptional = partVendorInformationRepository.findById(id);

        if (!partVendorInformationOptional.isPresent()) {
            throw new DatabaseNotFoundException();
        }

        partVendorInformationRepository.deleteById(id);

        return id;
    }
}
