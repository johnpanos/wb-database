package com.team3256.database.controller.inventory;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.inventory.*;
import com.team3256.database.service.PartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(PartController.class);

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private PartService partService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private PartVendorInformationRepository partVendorInformationRepository;

    @Secured("ROLE_USER")
    @GetMapping("/")
    public Page getParts(Pageable pageable) {
        logger.info("Getting all parts");
        return partRepository.findAll(pageable);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    public Part getPart(@PathVariable("id") Integer id) {
        Optional<Part> partOptional = partRepository.findById(id);

        logger.info("Getting part with id: " + id);

        if (partOptional.isPresent()) {
            return partOptional.get();
        } else {
            throw new DatabaseNotFoundException();
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/search")
    public Page searchParts(@RequestParam("q") String search, Pageable pageable) {
        logger.info("Searching parts with query: \"" + search + "\"");
        return partRepository.findByNameIgnoreCaseContaining(search, pageable);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_EDIT" })
    @PostMapping("/")
    public Part createPart(@Valid @RequestBody Part part) {
        return partRepository.save(part);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_EDIT" })
    @PutMapping("/{id}")
    public Part updatePart(@RequestBody Part part) {
        return partRepository.findById(part.getId()).map(dbPart -> {
            dbPart.setName(part.getName());
            dbPart.setSublocation(part.getSublocation());
            dbPart.setMinQuantity(part.getMinQuantity());
            dbPart.setNomenclature(part.getNomenclature());

            return partRepository.save(dbPart);
        }).orElseThrow(DatabaseNotFoundException::new);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_QUANTITY" })
    @PutMapping("/{id}/withdrawal")
    public Part withdrawal(@PathVariable Integer id, @RequestParam("q") int quantity) {
        Optional<Part> partOptional = partRepository.findById(id);

        if (!partOptional.isPresent()) {
            throw new DatabaseNotFoundException();
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

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_QUANTITY" })
    @PutMapping("/{id}/deposit")
    public Part deposit(@PathVariable Integer id, @RequestParam("q") Integer quantity) {
        Optional<Part> partOptional = partRepository.findById(id);

        if (!partOptional.isPresent()) {
            throw new DatabaseNotFoundException();
        }

        Part part = partOptional.get();

        part.setQuantity(part.getQuantity() + ((quantity < 0) ? 0 : quantity));

        return partRepository.save(part);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_EDIT" })
    @PutMapping("/{id}/{locationId}")
    public Part updatePartLocation(@PathVariable Integer id, @PathVariable("locationId") Integer locationId) {
        return partRepository.findById(id).map(part -> locationRepository.findById(locationId).map(location -> {
            part.setLocation(location);
            return partRepository.save(part);
        }).orElseThrow(DatabaseNotFoundException::new)).orElseThrow(DatabaseNotFoundException::new);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_EDIT" })
    @DeleteMapping("/{id}")
    public Integer deletePart(@PathVariable("id") Integer id) {
        return partRepository.findById(id).map(part -> {
            partService.delete(part);
            return id;
        }).orElseThrow(DatabaseNotFoundException::new);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_MENTOR", "ROLE_INV_EDIT" })
    @DeleteMapping("/")
    public Integer[] deleteParts(@RequestBody Integer[] ids) {
        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];
            Optional<Part> partOptional = partRepository.findById(id);

            if (partOptional.isPresent()) {
                Part part = partOptional.get();
                for (PartVendorInformation vendorInformation : part.getVendorInformation()) {
                    partVendorInformationRepository.delete(vendorInformation);
                }
                partRepository.deleteById(id);
            }
        }

        return ids;
    }
}