package com.team3256.database.controller.inventory;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.inventory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Secured("ROLE_USER")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page getParts(Pageable pageable) {
        return partRepository.findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Part getPart(@PathVariable("id") Integer id) {
        Optional<Part> partOptional = partRepository.findById(id);

        if (partOptional.isPresent()) {
            return partOptional.get();
        } else {
            throw new DatabaseNotFoundException("no part found with id - " + id);
        }
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Page searchParts(@RequestParam("q") String search, Pageable pageable) {
        System.out.println("SEARCH: " + search);
        return partRepository.findByNameIgnoreCaseContaining(search, pageable);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_CREATE" })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Part createPart(@Valid @RequestBody Part part) {
        return partRepository.save(part);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_UPDATE" })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Part updatePart(@RequestBody Part part) {
        Optional<Part> dbPartOptional = partRepository.findById(part.getId());

        if (dbPartOptional.isPresent()) {
            Part dbPart = dbPartOptional.get();

            dbPart.setName(part.getName());
            dbPart.setSublocation(part.getSublocation());
            dbPart.setMinQuantity(part.getMinQuantity());
            dbPart.setNomenclature(part.getNomenclature());

            return partRepository.save(dbPart);
        } else {
            throw new DatabaseNotFoundException("no part found with id - " + part.getId());
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}/withdrawal")
    public Part withdrawal(@PathVariable Integer id, @RequestParam("q") int quantity) {
        Optional<Part> partOptional = partRepository.findById(id);

        if (!partOptional.isPresent()) {
            throw new DatabaseNotFoundException("no part found with id - " + id);
        }

        Part part = partOptional.get();

        if (quantity > part.getQuantity()) {
            quantity = part.getQuantity();
        }

        if (quantity < 0) {
            quantity = 0;
        }

        part.setQuantity(part.getQuantity() - quantity);

        return partRepository.save(part);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}/deposit")
    public Part deposit(@PathVariable Integer id, @RequestParam("q") int quantity) {
        Optional<Part> partOptional = partRepository.findById(id);

        if (!partOptional.isPresent()) {
            throw new DatabaseNotFoundException("no part found with id - " + id);
        }

        if (quantity < 0) {
            quantity = 0;
        }

        Part part = partOptional.get();

        part.setQuantity(part.getQuantity() + quantity);

        return partRepository.save(part);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/{id}/{locationId}", method = RequestMethod.PUT)
    public Part updatePartLocation(@PathVariable Integer id, @PathVariable("locationId") Integer locationId) {
        Optional<Part> dbPartOptional = partRepository.findById(id);
        Optional<Location> dbLocationOptional = locationRepository.findById(locationId);

        if (!dbPartOptional.isPresent()) {
            throw new DatabaseNotFoundException("no part found with id - " + id);
        }

        if (!dbLocationOptional.isPresent()) {
            throw new DatabaseNotFoundException("no location found with id - " + locationId);
        }

        Part dbPart = dbPartOptional.get();
        Location dbLocation = dbLocationOptional.get();

        dbPart.setLocation(dbLocation);

        return partRepository.save(dbPart);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Integer deletePart(@PathVariable("id") Integer id) {
        Optional<Part> partOptional = partRepository.findById(id);

        if (partOptional.isPresent()) {
            Part part = partOptional.get();
            for (PartVendorInformation vendorInformation : part.getVendorInformation()) {
                partVendorInformationRepository.delete(vendorInformation);
            }
            partRepository.deleteById(id);
            return id;
        }

        throw new DatabaseNotFoundException("no part found with id - " + id);
    }
}