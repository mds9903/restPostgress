package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.response.DemandResponse;
import com.mdsujan.restPostgres.response.DemandDetailsResponse;
import com.mdsujan.restPostgres.response.DemandResponse;
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

    @GetMapping("/")
    public List<DemandResponse> getAllDemands() {
        List<Demand> demandList = demandService.getAll();
        List<DemandResponse> demandResponseList = new ArrayList<>();

        demandList.forEach(demand -> demandResponseList.add(new DemandResponse(demand)));
        return demandResponseList;
    }

    @GetMapping("/{demandId}")
    public DemandResponse getDemandById(@PathVariable Long demandId) {
        return new DemandResponse(demandService.getDemandById(demandId));
    }

    @GetMapping("/getDemandList/{itemId}/{locationId}")
    public List<DemandResponse> getDemandsByItemAndLocation(@PathVariable Long itemId, @PathVariable Long locationId) {
        List<Demand> demandList = demandService.getDemandsByItemIdAndLocationId(itemId, locationId);
        List<DemandResponse> demandResponseList = new ArrayList<>();

        demandList.forEach(demand -> demandResponseList.add(new DemandResponse(demand)));

        return demandResponseList;

    }

    @GetMapping("/{itemId}/{locationId}")
    public DemandDetailsResponse getDemandDetailsByItemAndLocation(@PathVariable Long itemId, @PathVariable Long locationId) {
        return demandService.getDemandDetailsByItemAndLocation(itemId, locationId);
    }


    @PostMapping("/")
    public DemandResponse createDemand(@RequestBody CreateDemandRequest createDemandRequest) {
        return new DemandResponse(demandService.createNewDemand(createDemandRequest));
    }
}
