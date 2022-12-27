package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.SupplyDetailsResponse;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/supply")
@CrossOrigin(origins = "http://localhost:3000")

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
    public SupplyResponse getSupplyById(@PathVariable Long supplyId) throws Throwable {
        return new SupplyResponse(supplyService.getSupplyById(supplyId));
    }

    @GetMapping("/{itemId}/{locationId}") // get supply details for an item at a location
    public SupplyDetailsResponse getSupplyDetailsByItemAndLocation(@PathVariable @Valid Long itemId, @PathVariable @Valid Long locationId) throws Throwable {
        return supplyService.getSupplyDetailsByItemAndLocation(itemId, locationId);
    }

    // creates ambiguity - solution: 1. use regex; 2. perform the validation of the path vars in the method and call the relevant service
//    @GetMapping("/{supplyType}/{locationId}") // get demand details by itemType and location
////    public SupplyDetailsResponse getSupplyDetailsBySupplyTypeAndLocation(@PathVariable Long supplyType, @PathVariable Long locationId) throws Throwable {
//    public List<Supply> getSupplyDetailsBySupplyTypeAndLocation(@PathVariable Long supplyType, @PathVariable Long locationId) throws Throwable {
//        return supplyService.getSupplyDetailsBySupplyTypeAndLocation(supplyType, locationId);
//    }

    @PostMapping("/") // create a new supply
    public SupplyResponse createSupply(@RequestBody CreateSupplyRequest createSupplyRequest) throws Throwable {
//        // send the supply entity itself that should be created
//        Supply newSupply = new Supply(createSupplyRequest); // without the details of the (dependency) item and location entities
//        // pass the request body to help assign item and location as well as pass the supply object for creating
        return new SupplyResponse(supplyService.createNewSupply(createSupplyRequest));
    }

    @PutMapping("/{supplyId}") // update supply using PUT
    public SupplyResponse updateSupplyPut(@PathVariable @Valid Long supplyId, @RequestBody @Valid UpdateSupplyRequest updateSupplyRequest) throws Throwable {
        return new SupplyResponse(supplyService.updateSupplyPut(supplyId, updateSupplyRequest));
    }

    @PatchMapping("/{supplyId}") // update supply using PATCH
    public SupplyResponse updateSupplyPatch(@PathVariable @Valid Long supplyId, @RequestBody UpdateSupplyRequest updateSupplyRequest) throws Throwable {
        return new SupplyResponse(supplyService.updateSupplyPatch(supplyId, updateSupplyRequest));
    }


    @DeleteMapping("/{supplyId}") // delete a supply
    public String deleteSupply(@PathVariable Long supplyId) throws Throwable {
        return supplyService.deleteSupply(supplyId);
    }
}
