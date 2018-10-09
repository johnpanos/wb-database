package com.team3256.database.controller;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.inventory.Location;
import com.team3256.database.model.inventory.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/inventory/location")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page getLocations(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Location getLocation(@PathVariable("id") Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);

        if (!locationOptional.isPresent()) {
            throw new DatabaseNotFoundException("no location with id - " + id);
        }

        return locationOptional.get();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Location updateLocation(@RequestBody Location location) {
        Optional<Location> locationOptional = locationRepository.findById(location.getId());

        if (!locationOptional.isPresent()) {
            throw new DatabaseNotFoundException("no location found with id - " + location.getId());
        }

        Location dbLocation = locationOptional.get();
        dbLocation.setName(location.getName());
        locationRepository.save(dbLocation);
        return dbLocation;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Long deleteLocation(@PathVariable("id") Long id) {
        Optional<Location> location = locationRepository.findById(id);

        if (!location.isPresent()) {
            throw new DatabaseNotFoundException("no location found with id - " + id);
        }

        locationRepository.delete(location.get());
        return location.get().getId();
    }
}
