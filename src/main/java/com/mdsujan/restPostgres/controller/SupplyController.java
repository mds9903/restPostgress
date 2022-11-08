package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/supply")
public class SupplyController {
    @Autowired
    SupplyService supplyService;

    @GetMapping("/")
    public List<SupplyResponse> getSupplies() {
        List<Supply> supplyList = supplyService.getAllSupplies();
        List<SupplyResponse> supplyResponseList = new ArrayList<>();

        supplyList.forEach(
                supply -> supplyResponseList.add(new SupplyResponse(supply))
        );
        return supplyResponseList;
    }
}
