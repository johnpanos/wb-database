package com.team3256.database.controller;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.inventory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory/part")
public class PartController {

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private PartVendorInformationRepository partVendorInformationRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page getParts(Pageable pageable) {
        return partRepository.findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Part getPart(@PathVariable("id") Long id) {
        Optional<Part> partOptional = partRepository.findById(id);

        if (partOptional.isPresent()) {
            return partOptional.get();
        } else {
            throw new DatabaseNotFoundException("no part found with id - " + id);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Part> searchParts(@RequestParam("q") String search) {
        System.out.println("SEARCH: " + search);
        return partRepository.findByNameIgnoreCaseContaining(search);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Part createPart(@Valid @RequestBody Part part) {
        locationRepository.save(part.getLocation());
        return partRepository.save(part);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Part updatePart(@RequestBody Part part) {
        Optional<Part> dbPartOptional = partRepository.findById(part.getId());

        if (dbPartOptional.isPresent()) {
            Part dbPart = dbPartOptional.get();

            dbPart.setName(part.getName());
            dbPart.setSublocation(part.getSublocation());
            dbPart.setQuantity(part.getQuantity());
            dbPart.setMinQuantity(part.getMinQuantity());
            dbPart.setNomenclature(part.getNomenclature());

            return partRepository.save(dbPart);
        } else {
            throw new DatabaseNotFoundException("no part found with id - " + part.getId());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deletePart(@PathVariable("id") Long id) {
        Optional<Part> partOptional = partRepository.findById(id);

        if (partOptional.isPresent()) {
            Part part = partOptional.get();
            partRepository.delete(part);
            return "yay";
        }
        throw new DatabaseNotFoundException("no part found with id - " + id);
    }

    @RequestMapping(value = "/vendor-info/{id}", method = RequestMethod.POST)
    public Part addPartVendorInformation(@PathVariable("id") Long id, @RequestBody PartVendorInformation partVendorInformation, @RequestParam("vendor") Long vendorId) {
        Optional<Part> partOptional = partRepository.findById(id);
        Optional<Vendor> vendorOptional = vendorRepository.findById(vendorId);

        if (!partOptional.isPresent()) {
            throw new DatabaseNotFoundException("no part found with id - " + id);
        }

        if (!vendorOptional.isPresent()) {
            throw new DatabaseNotFoundException("no vendor found with id - " + vendorId);
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
}