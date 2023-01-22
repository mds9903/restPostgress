package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.request.*;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.response.DemandResponse;
import com.mdsujan.restPostgres.response.DemandDetailsResponse;
import com.mdsujan.restPostgres.response.DemandResponse;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory/demand")
@CrossOrigin(origins = "http://localhost:3000")

public class DemandController {

    @Autowired
    DemandService demandService;

    @GetMapping("/") // get all demands
    public List<DemandResponse> getAllDemands() {
        List<Demand> demandList = demandService.getAll();
        List<DemandResponse> demandResponseList = new ArrayList<>();

        demandList.forEach(demand -> demandResponseList.add(new DemandResponse(demand)));
        return demandResponseList;
    }

    @GetMapping("/{demandId}") // get demand by demandId
    public DemandResponse getDemandById(@PathVariable @Valid Long demandId) throws Throwable {
        return new DemandResponse(demandService.getDemandById(demandId));
    }

    @GetMapping("/{itemId}/{locationId}") // get demand details by item and location
    public DemandDetailsResponse getDemandDetailsByItemAndLocation(@PathVariable @Valid Long itemId,
                                                                   @PathVariable @Valid Long locationId) throws Throwable {
        return demandService.getDemandDetailsByItemAndLocation(itemId, locationId);
    }


    @PostMapping("/") // create a new demand
    public DemandResponse createDemand(@RequestBody CreateDemandRequest createDemandRequest) throws Throwable {
        return new DemandResponse(demandService.createNewDemand(createDemandRequest));
    }

    @PostMapping("/batch") // create a new demand
    public List<DemandResponse> createDemands(@RequestBody List<CreateDemandRequest> createDemandRequests) throws Throwable {
//        // send the demand entity itself that should be created
//        Demand newDemand = new Demand(createDemandRequest); // without the details of the (dependency) item and location entities
//        // pass the request body to help assign item and location as well as pass the demand object for creating
        return (demandService.createNewDemands(createDemandRequests));
    }

    @PutMapping("/{demandId}") // update a demand using PUT
    public DemandResponse updateDemandPut(@PathVariable @Valid Long demandId,
                                          @RequestBody @Valid UpdateDemandRequest updateDemandRequest) throws Throwable {
        return new DemandResponse(demandService.updateDemandPut(demandId, updateDemandRequest));
    }

    @PatchMapping("/{demandId}") // update a demand using PATCH
    public DemandResponse updateDemandPatch(@PathVariable @Valid Long demandId,
                                            @RequestBody UpdateDemandRequest updateDemandRequest) throws Throwable {
        return new DemandResponse(demandService.updateDemandPatch(demandId, updateDemandRequest));
    }

    @DeleteMapping("/{demandId}") // delete a demand
    public String deleteDemand(@PathVariable @Valid Long demandId) throws Throwable {
        return demandService.deleteDemand(demandId);
    }
}
