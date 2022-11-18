package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.request.UpdateDemandRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.DemandResponse;
import com.mdsujan.restPostgres.response.DemandDetailsResponse;
import com.mdsujan.restPostgres.response.DemandResponse;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demand")
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
    public DemandResponse getDemandById(@PathVariable Long demandId) throws Throwable {
        return new DemandResponse(demandService.getDemandById(demandId));
    }

//    // not included in the requirements sheet
//    @GetMapping("/demandList/{itemId}/{locationId}") // get list of demands by item and location
//    public List<DemandResponse> getDemandsByItemAndLocation(@PathVariable Long itemId, @PathVariable Long locationId) {
//        List<Demand> demandList = demandService.getDemandsByItemIdAndLocationId(itemId, locationId);
//        List<DemandResponse> demandResponseList = new ArrayList<>();
//
//        demandList.forEach(demand -> demandResponseList.add(new DemandResponse(demand)));
//
//        return demandResponseList;
//
//    }

    // not included in the requirements sheet
    @GetMapping("demandList/{itemId}") // get demand for an item in all locations
    public List<DemandResponse> getDemandsByItem(@PathVariable Long itemId) {
        List<Demand> demandList = demandService.getDemandsByItemId(itemId);
        List<DemandResponse> demandResponseList = new ArrayList<>();

        demandList.forEach(demand -> demandResponseList.add(new DemandResponse(demand)));

        return demandResponseList;
    }

    @GetMapping("/{itemId}/{locationId}") // get demand details by item and location
    public DemandDetailsResponse getDemandDetailsByItemAndLocation(@PathVariable Long itemId, @PathVariable Long locationId) throws Throwable {
        return demandService.getDemandDetailsByItemAndLocation(itemId, locationId);
    }

    @PostMapping("/") // create a new demand
    public DemandResponse createDemand(@RequestBody CreateDemandRequest createDemandRequest) throws Throwable {
        return new DemandResponse(demandService.createNewDemand(createDemandRequest));
    }

    @PutMapping("/{demandId}") // update a demand (all fields)
    public DemandResponse updateDemandPut(@PathVariable Long demandId, @RequestBody UpdateDemandRequest updateDemandRequest) throws Throwable {
        return new DemandResponse(demandService.updateDemandPut(demandId, updateDemandRequest));
    }

    @PatchMapping("/{demandId}") // update a demand (all fields)
    public DemandResponse updateDemandPatch(@PathVariable Long demandId, @RequestBody UpdateDemandRequest updateDemandRequest) throws Throwable {
        return new DemandResponse(demandService.updateDemandPatch(demandId, updateDemandRequest));
    }

    @DeleteMapping("/{demandId}") // delete a demand
    public String deleteSupply(@PathVariable Long demandId) throws Throwable {
        return demandService.deleteDemand(demandId);
    }
}
