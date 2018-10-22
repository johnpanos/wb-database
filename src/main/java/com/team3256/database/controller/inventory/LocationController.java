package com.team3256.database.controller.inventory;

import com.team3256.database.error.DatabaseAlreadyExistsException;
import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.inventory.*;
import com.team3256.database.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/inventory/location")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private PartService partService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page getLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Location getLocation(@PathVariable("id") Integer id) {
        return locationRepository.findById(id).orElseThrow(DatabaseNotFoundException::new);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Location createLocation(@RequestBody Location location) {
        Optional<Location> locationOptional = locationRepository.findByNameIgnoringCase(location.getName());

        if (locationOptional.isPresent()) {
            throw new DatabaseAlreadyExistsException("location with name already exists");
        }

        return locationRepository.save(location);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Location updateLocation(@RequestBody Location location) {
        return locationRepository.findById(location.getId()).map(dbLocation -> {
            dbLocation.setName(location.getName());
            return locationRepository.save(dbLocation);
        }).orElseThrow(DatabaseNotFoundException::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Integer deleteLocation(@PathVariable("id") Integer id) {
        return locationRepository.findById(id).map(location -> {
            for (Part part : location.getParts()) {
                System.out.println("Deleteing part " + part.getName());
                partService.delete(part);
            }
            locationRepository.delete(location);
            return id;
        }).orElseThrow(DatabaseNotFoundException::new);
    }
}
