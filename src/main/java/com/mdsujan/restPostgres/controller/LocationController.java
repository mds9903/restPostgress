package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.request.CreateLocationRequest;
import com.mdsujan.restPostgres.request.UpdateLocationRequest;
import com.mdsujan.restPostgres.response.LocationResponse;
import com.mdsujan.restPostgres.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    LocationService locationService;

    @GetMapping("/") // return all locations from table location
    public List<LocationResponse> getLocations() {
        List<Location> itemList = locationService.getAllLocations();
        List<LocationResponse> itemResponseList = new ArrayList<>();

        // convert each Location obj to LocationResponse
        itemList.stream().forEach(item -> itemResponseList.add(new LocationResponse(item)));
        return itemResponseList;
    }

    @GetMapping("/{locationId}") // return the details of specific locationId
    public LocationResponse getLocationWithId(@PathVariable Long locationId) {
        return new LocationResponse(locationService.getLocationById(locationId));
    }

    @PostMapping("/") // create an item in the table
    public LocationResponse createLocation(@RequestBody CreateLocationRequest createLocationRequest) {
        Location newLocation = locationService.createLocation(createLocationRequest);
        return new LocationResponse(newLocation);
    }

    @DeleteMapping("/{locationId}") // delete a specific item
    public String deleteLocation(@PathVariable Long locationId) {
        boolean deleted = locationService.deleteLocationById(locationId);
        if (deleted) {
            return "Location deleted successfully";
        }
        return "Location not deleted";
    }

    @PutMapping("/{locationId}")
    public Location updateLocationPut(@PathVariable Long locationId, @RequestBody UpdateLocationRequest updateLocationRequest) {
        return locationService.updateLocationPut(locationId, updateLocationRequest);
    }

    @PatchMapping("/{locationId}")
    public Location updateLocationPatch(@PathVariable Long locationId, @RequestBody UpdateLocationRequest updateLocationRequest) {
        return locationService.updateLocationPatch(locationId, updateLocationRequest);
    }
}