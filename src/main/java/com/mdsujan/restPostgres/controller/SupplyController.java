package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.response.SupplyDetails;
import com.mdsujan.restPostgres.response.SupplyDetailsResponse;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.service.SupplyService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/supply")
public class SupplyController {
    @Autowired
    SupplyService supplyService;

    @GetMapping("/")
    public List<SupplyResponse> getAllSupplies() {
        List<Supply> supplyList = supplyService.getAllSupplies();
        List<SupplyResponse> supplyResponseList = new ArrayList<>();

        supplyList.forEach(
                supply -> supplyResponseList.add(new SupplyResponse(supply))
        );
        return supplyResponseList;
    }

    @GetMapping("/{supplyId}")
    public SupplyResponse getSupplyById(@PathVariable Long supplyId) {
        return new SupplyResponse(supplyService.getSupplyById(supplyId));
    }

    @GetMapping("/getSupplyList/{itemId}/{locationId}")
    public List<SupplyResponse> getSuppliesByItemAndLocation(@PathVariable Long itemId, @PathVariable Long locationId) {
        List<Supply> supplyList = supplyService.getSuppliesByItemIdAndLocationId(itemId, locationId);
        List<SupplyResponse> supplyResponseList = new ArrayList<>();

        supplyList.forEach(supply -> supplyResponseList.add(new SupplyResponse(supply)));

        return supplyResponseList;

    }

    @GetMapping("/{itemId}/{locationId}")
    public SupplyDetailsResponse getSupplyDetailsByItemAndLocation(@PathVariable Long itemId, @PathVariable Long locationId) {
        return supplyService.getSupplDetailsByItemAndLocation(itemId, locationId);
    }

    @PostMapping("/")
    public SupplyResponse createSupply(@RequestBody CreateSupplyRequest createSupplyRequest) {
        return new SupplyResponse(supplyService.createNewSupply(createSupplyRequest));
    }


}
