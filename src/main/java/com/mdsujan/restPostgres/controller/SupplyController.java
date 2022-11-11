package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.SupplyDetailsResponse;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/supply")
public class SupplyController {
    @Autowired
    SupplyService supplyService;

    @GetMapping("/") // get all supplies
    public List<SupplyResponse> getAllSupplies() {
        List<Supply> supplyList = supplyService.getAllSupplies();
        List<SupplyResponse> supplyResponseList = new ArrayList<>();

        supplyList.forEach(
                supply -> supplyResponseList.add(new SupplyResponse(supply))
        );
        return supplyResponseList;
    }

    @GetMapping("/{supplyId}") // get supply by id
    public SupplyResponse getSupplyById(@PathVariable Long supplyId) {
        return new SupplyResponse(supplyService.getSupplyById(supplyId));
    }

    // not included in the requirements sheet
    @GetMapping("supplyList/{itemId}/{locationId}") // get list of supplies by item and location
    public List<SupplyResponse> getSuppliesByItemAndLocation(@PathVariable Long itemId, @PathVariable Long locationId) {
        List<Supply> supplyList = supplyService.getSuppliesByItemIdAndLocationId(itemId, locationId);
        List<SupplyResponse> supplyResponseList = new ArrayList<>();

        supplyList.forEach(supply -> supplyResponseList.add(new SupplyResponse(supply)));

        return supplyResponseList;

    }
    // not included in the requirements sheet
    @GetMapping("supplyList/{itemId}") // get supply for an item in all locations
    public List<SupplyResponse> getSuppliesByItem(@PathVariable Long itemId) {
        List<Supply> supplyList = supplyService.getSuppliesByItemId(itemId);
        List<SupplyResponse> supplyResponseList = new ArrayList<>();

        supplyList.forEach(supply -> supplyResponseList.add(new SupplyResponse(supply)));

        return supplyResponseList;
    }
    @GetMapping("/{itemId}/{locationId}") // get supply details for an item at a location
    public SupplyDetailsResponse getSupplyDetailsByItemAndLocation(@PathVariable Long itemId, @PathVariable Long locationId) {
        return supplyService.getSupplDetailsByItemAndLocation(itemId, locationId);
    }

    @PostMapping("/") // create a new supply
    public SupplyResponse createSupply(@RequestBody CreateSupplyRequest createSupplyRequest) {
        return new SupplyResponse(supplyService.createNewSupply(createSupplyRequest));
    }

    @PutMapping("/{supplyId}") // update supply (all fields)
    public SupplyResponse updateSupply(@PathVariable Long supplyId , @RequestBody UpdateSupplyRequest updateSupplyRequest){
        return new SupplyResponse(supplyService.updateSupply(supplyId ,updateSupplyRequest));
    }

    @DeleteMapping("/{supplyId}") // delete a supply
    public String deleteSupply(@PathVariable Long supplyId){
        if(supplyService.deleteSupply(supplyId)){
            return "Supply successfully";
        }
        return "Supply not deleted";
    }
}
