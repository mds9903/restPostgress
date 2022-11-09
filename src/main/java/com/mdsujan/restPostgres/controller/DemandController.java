package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.repository.DemandRepository;
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
    public List<DemandResponse> getAllDemands(){
        List<Demand> demandList = demandService.getAll();
        List<DemandResponse> demandResponseList = new ArrayList<>();

        demandList.forEach(demand -> demandResponseList.add(new DemandResponse(demand)));
        return demandResponseList;
    }

    @GetMapping("/{demandId}")
    public DemandResponse getDemandById(@PathVariable Long demandId){
        return new DemandResponse(demandService.getDemandById(demandId));
    }
}
