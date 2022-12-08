package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.response.AvailabilityV3Response;
import com.mdsujan.restPostgres.response.ThresholdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/v3/availability")
public class AvailabilityV3Controller {
    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    DemandRepository demandRepository;

    @Autowired
    ThresholdRepository thresholdRepository;



    @Value("${supplyTypes}")
    private String supplyTypes;

    @Value("${demandTypes}")
    private String demandTypes;

    @GetMapping("/{itemId}/{locationId}")
    public AvailabilityV3Response getAvailabilityStockLevel(@PathVariable Long itemId, @PathVariable Long locationId) {
        // get the availabilityQty for given itemId and locationId
        List<Supply> supplyList = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
        Long supplyQty = 0L;
        List<String> supplyTypeList = List.of(supplyTypes); // get the supply types from the config

        List<Demand> demandList = demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
        Long demandQty = 0L;
        List<String> demandTypeList = List.of(demandTypes);  // get the supply types from the config

        System.out.printf("supplyTypeList : "+supplyTypeList);
        System.out.printf("demandTypeList : "+demandTypeList);

        for (String supplyType : supplyTypeList) {
            supplyQty += supplyList.stream()
                    .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.valueOf(supplyType))
                    .map(Supply::getSupplyQty)
                    .reduce(0L, Long::sum);
        }
        for (String demandType : demandTypeList) {
            demandQty += demandList.stream()
                    .filter(demand -> demand.getDemandType() == AllowedDemandTypes.valueOf(demandType))
                    .map(Demand::getDemandQty)
                    .reduce(0L, (a, b) -> a + b);
        }
        Long availableQty = supplyQty + demandQty;
        /*
         *
         *  if availabilityQty < minThreshold then red
         *  if availability > maxThreshold then green
         *  else yellow (availabilityQty >= min and <= max)
         *
         * */
        Threshold threshold = thresholdRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
        System.out.println(threshold);
        String stockLevel = "yellow";
        if (availableQty < threshold.getMinThreshold()) {
            stockLevel = "Red";
        } else if (availableQty > threshold.getMaxThreshold()) {
            stockLevel = "Green";
        }
        return new AvailabilityV3Response(itemId, locationId, availableQty, stockLevel);
    }
}