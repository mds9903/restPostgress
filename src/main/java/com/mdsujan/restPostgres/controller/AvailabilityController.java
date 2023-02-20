package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.response.AvailabilityV1Response;
import com.mdsujan.restPostgres.response.AvailabilityV2Response;
import com.mdsujan.restPostgres.response.AvailabilityV3Response;
import com.mdsujan.restPostgres.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/inventory/")
@CrossOrigin(origins = "http://localhost:3000")

public class AvailabilityController {

    @Autowired
    AvailabilityService availabilityService;

    @GetMapping("/v1/availability/{itemId}/{locationId}")
    // get availability qty of item at a given location
    public AvailabilityV1Response getAvlQtyByItemAndLocationV1(
            @PathVariable Long itemId,
            @PathVariable Long locationId) throws Throwable {
        // get availability quantity by item and location
        return availabilityService.getAvlQtyByItemAndLocationV1(itemId, locationId);
    }

    @GetMapping("/v1/availability/{itemId}")
    // get availability qty of item in all locations (total)
    public AvailabilityV1Response getAllLocationAvailabilityQtyV1(
            @PathVariable Long itemId) throws Throwable {
        // get availability quantity by item for all locations
        return availabilityService.getAvlQtyByItemV1(itemId);
    }

    @GetMapping("/v2/availability/{itemId}/{locationId}")
    // get the stock level for an item at a location v2
    public AvailabilityV2Response getAvailabilityStockLevelV2(
            @PathVariable Long itemId,
            @PathVariable Long locationId) throws ResourceNotFoundException {
        // get the stock level "red", "yellow" or "green" for an item and a location
        return availabilityService.getStockLevelV2(itemId, locationId);
    }

    @GetMapping("/v3/availability/{itemId}/{locationId}")
    // get the stock level for an item at a location v3
    public AvailabilityV3Response getAvailabilityStockLevelV3(
            @PathVariable Long itemId,
            @PathVariable Long locationId) throws ResourceNotFoundException {
        // get the stock level but only as mentioned in the config file (properties file)
        return availabilityService.getStockLevelV3(itemId, locationId);
    }
}
