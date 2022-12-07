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

    @GetMapping("/{itemId}/{locationId}")
    public AvailabilityV3Response getAvailabilityStockLevel(@PathVariable Long itemId, @PathVariable Long locationId) {
        Properties prop = new Properties();
        String fileName = "/home/sujan/Documents/restPostgres/src/main/java/com/mdsujan/restPostgres/availability.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            // get the availabilityQty for given itemId and locationId
            List<Supply> supplyList = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
            List<Demand> demandList = demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
            // for supplies
            Long supplyQty = 0L;
            List<String> supplyTypes = List.of(prop.getProperty("supplies").split(","));
            for (String supplyType : supplyTypes) {
                supplyQty += supplyList
                        .stream()
                        .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.valueOf(supplyType))
                        .map(Supply::getSupplyQty)
                        .reduce(0L, Long::sum);
            }
            Long demandQty = 0L;
            List<String> demandTypes = List.of(prop.getProperty("demands").split(","));
            for (String demandType : demandTypes) {
                demandQty += demandList.stream().filter(demand -> demand.getDemandType() == AllowedDemandTypes.valueOf(demandType)).map(Demand::getDemandQty).reduce(0L, (a, b) -> a + b);
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
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("exception occurred: " + e.getMessage());
        }
        return null;
    }
}