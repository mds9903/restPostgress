package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/locations")
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

    @PostMapping("/") // create an item in the table
    public Location createLocation(@RequestBody Location createLocationRequest) throws Throwable {
        return locationService.createLocation(createLocationRequest);
    }

    @DeleteMapping("/{locationId}") // delete a specific item
    public String deleteLocation(@PathVariable Long locationId) throws Throwable {
        return locationService.deleteLocationById(locationId);
    }

    @PutMapping("/{locationId}")
    public Location updateLocationPut(@PathVariable @Valid Long locationId, @RequestBody @Valid Location updateLocationRequest) throws Throwable {
        return locationService.updateLocationPut(locationId, updateLocationRequest);
    }

    @PatchMapping("/{locationId}")
    public Location updateLocationPatch(@PathVariable @Valid Long locationId, @RequestBody Location updateLocationRequest) throws Throwable {
        return locationService.updateLocationPatch(locationId, updateLocationRequest);
    }
}