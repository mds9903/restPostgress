package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.request.CreateThresholdRequest;
import com.mdsujan.restPostgres.request.UpdateThresholdRequest;
import com.mdsujan.restPostgres.response.ThresholdDetails;
import com.mdsujan.restPostgres.response.ThresholdDetailsResponse;
import com.mdsujan.restPostgres.response.ThresholdResponse;
import com.mdsujan.restPostgres.service.ThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/atpThresholds")
public class ThresholdController {

    @Autowired
    ThresholdService thresholdService;

    @GetMapping("/")
    public List<ThresholdResponse> getAllThresholds(){
        List<Threshold> thresholdList = thresholdService.getAllThresholds();
        List<ThresholdResponse> thresholdResponseList = new ArrayList<>();

        thresholdList.forEach(threshold -> thresholdResponseList.add(new ThresholdResponse(threshold)));

        return thresholdResponseList;
    }

    @GetMapping("/{thresholdId}")
    public ThresholdResponse getThreshold(@PathVariable Long thresholdId){
        return new ThresholdResponse(thresholdService.getThreshold(thresholdId));
    }

    @GetMapping("/{itemId}/{locationId}")
    public ThresholdDetailsResponse getThresholdDetails(@PathVariable Long itemId, @PathVariable Long locationId){
        return thresholdService.getThresholdDetailsByItemAndLocation(itemId, locationId);
    }

    @PostMapping("/")
    public ThresholdResponse createThreshold(@RequestBody CreateThresholdRequest createThresholdRequest){
        return new ThresholdResponse(thresholdService.createThreshold(createThresholdRequest));
    }

    @PutMapping("/{thresholdId}")
    public ThresholdResponse updateThresholdPut(@PathVariable Long thresholdId ,@RequestBody UpdateThresholdRequest updateThresholdRequest){
        return new ThresholdResponse(thresholdService.updateThresholdPut(thresholdId, updateThresholdRequest));
    }
    @PatchMapping("/{thresholdId}")
    public ThresholdResponse updateThresholdPatch(@PathVariable Long thresholdId ,@RequestBody UpdateThresholdRequest updateThresholdRequest){
        return new ThresholdResponse(thresholdService.updateThresholdPatch(thresholdId, updateThresholdRequest));
    }

//    @PutMapping("/{itemId}/{locationId}")
//    public ThresholdResponse updateThresholdPut(@PathVariable Long itemId, @PathVariable Long locationId,@RequestBody UpdateThresholdRequest updateThresholdRequest){
//        return new ThresholdResponse(thresholdService.updateThresholdDetailsPut(itemId, locationId, updateThresholdRequest));
//    }
//    @PatchMapping("/{itemId}/{locationId}")
//    public ThresholdResponse updateThresholdPatch(@PathVariable Long itemId, @PathVariable Long locationId ,@RequestBody UpdateThresholdRequest updateThresholdRequest){
//        return new ThresholdResponse(thresholdService.updateThresholdDetailsPatch(itemId, locationId,updateThresholdRequest));
//    }
    @DeleteMapping("/{thresholdId}") // delete a specific item
    public String deleteThreshold(@PathVariable Long thresholdId) {
        return thresholdService.deleteThresholdById(thresholdId);
    }

}
