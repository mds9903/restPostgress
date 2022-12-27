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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/atpThresholds")
@CrossOrigin(origins = "http://localhost:3000")

public class ThresholdController {

    @Autowired
    ThresholdService thresholdService;

    @GetMapping("/") // get all thresholds
    public List<ThresholdResponse> getAllThresholds() {
        List<Threshold> thresholdList = thresholdService.getAllThresholds();
        List<ThresholdResponse> thresholdResponseList = new ArrayList<>();

        thresholdList.forEach(threshold -> thresholdResponseList.add(new ThresholdResponse(threshold)));

        return thresholdResponseList;
    }

    @GetMapping("/{thresholdId}")
    public ThresholdResponse getThreshold(@PathVariable Long thresholdId) throws Throwable {
        return new ThresholdResponse(thresholdService.getThreshold(thresholdId));
    }

    @GetMapping("/{itemId}/{locationId}")
    public ThresholdDetailsResponse getThresholdDetails(@PathVariable @Valid Long itemId,
                                                        @PathVariable @Valid Long locationId) {
        return thresholdService.getThresholdDetailsByItemAndLocation(itemId, locationId);
    }

    @PostMapping("/")
    public ThresholdResponse createThreshold(@RequestBody @Valid CreateThresholdRequest createThresholdRequest) throws Throwable {
        return new ThresholdResponse(thresholdService.createThreshold(createThresholdRequest));
    }

    @PutMapping("/{thresholdId}")
    public ThresholdResponse updateThresholdPut(@PathVariable @Valid Long thresholdId,
                                                @RequestBody @Valid UpdateThresholdRequest updateThresholdRequest) throws Throwable {
        return new ThresholdResponse(thresholdService.updateThresholdPut(thresholdId, updateThresholdRequest));
    }

    @PatchMapping("/{thresholdId}")
    public ThresholdResponse updateThresholdPatch(@PathVariable @Valid Long thresholdId,
                                                  @RequestBody UpdateThresholdRequest updateThresholdRequest) throws Throwable {
        return new ThresholdResponse(thresholdService.updateThresholdPatch(thresholdId, updateThresholdRequest));
    }

    @PutMapping("/{itemId}/{locationId}")
    public Boolean updateThresholdPut(@PathVariable @Valid Long itemId, @PathVariable @Valid Long locationId,
                                      @RequestBody @Valid UpdateThresholdRequest updateThresholdRequest) throws Throwable {
        return thresholdService.updateThresholdByItemAndLocationPut(itemId, locationId, updateThresholdRequest);
    }

    @DeleteMapping("/{thresholdId}") // delete a specific item
    public String deleteThreshold(@PathVariable @Valid Long thresholdId) throws Throwable {
        return thresholdService.deleteThresholdById(thresholdId);
    }

}
