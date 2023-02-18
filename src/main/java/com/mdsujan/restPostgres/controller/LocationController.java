package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/inventory/locations")
@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {
    @Autowired
    LocationService locationService;

    @GetMapping("/") // return all locations from table location
    public List<Location> getLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{locationId}") // return the details of specific locationId
    public Location getLocationWithId(@PathVariable Long locationId) throws Throwable {
        return locationService.getLocationById(locationId);
    }

    @PostMapping("/") // create a location in the table
    public Location createLocation(@RequestBody Location createLocationRequest) throws Throwable {
        return locationService.createLocation(createLocationRequest);
    }

    @PostMapping("/batch") // create locations (batch) in the table
    public List<Location> createLocations(@RequestBody List<Location> createLocationRequestList) throws Throwable {
//        logger.info("InQueryRequest: " + createLocationRequestList);
        List<Location> locationResponse = locationService.createLocations(createLocationRequestList);
//        logger.info("Response: " + locationResponse);
        return locationResponse;
    }

    @DeleteMapping("/{locationId}") // delete a specific location
    public String deleteLocation(@PathVariable Long locationId) throws Throwable {
        return locationService.deleteLocationById(locationId);
    }

    @PutMapping("/{locationId}") // update a location using PUT
    public Location updateLocationPut(@PathVariable @Valid Long locationId, @RequestBody @Valid Location updateLocation) throws Throwable {
        return locationService.updateLocationPut(locationId, updateLocation);
    }

    @PatchMapping("/{locationId}") // update a location using PATCH
    public Location updateLocationPatch(@PathVariable @Valid Long locationId, @RequestBody Location updateLocation) throws Throwable {
        return locationService.updateLocationPatch(locationId, updateLocation);
    }
}