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
import java.util.Properties;
@RestController
@RequestMapping("/v3/availability")
public class AvailabilityV3Controller {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    DemandRepository demandRepository;

    @Autowired
    ThresholdRepository thresholdRepository;

    @GetMapping("/{itemId}/{locationId}")
//    public AvailabilityV3Response getAvailabilityStockLevel(@PathVariable Long itemId, @PathVariable Long locationId) {
    public String getAvailabilityStockLevel(@PathVariable Long itemId, @PathVariable Long locationId) {
        Properties prop = new Properties();
        String fileName = "/home/sujan/Documents/restPostgres/src/main/java/com/mdsujan/restPostgres/availability.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("exception occurred: " + e.getMessage());
        }
        System.out.println("supplies " + prop.getProperty("supplies"));
        System.out.println("demands " + prop.getProperty("demands"));
        System.out.println("locations.exclude " + prop.getProperty("locations.exclude"));
        return prop.getProperty("supplies")
                + "\n" + prop.getProperty("demands")
                + "\n" + prop.getProperty("locations.exclude");
//        // get the availabilityQty for given itemId and locationId
//        Long onhandQty = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId)
//                .stream()
//                .filter(supply -> supply.getSupplyType() == availabilityConfig)
//                .map(Supply::getQty)
//                .reduce(0L, (a, b) -> a + b);
//        // get the hardPromised qty for the demand of the given item and location
//        Long hardPromisedQty = demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId)
//                .stream()
//                .filter(demand -> demand.getDemandType() == AllowedDemandTypes.HARD_PROMISED)
//                .map(Demand::getDemandQty)
//                .reduce(0L, (a, b) -> a + b);
//
//        Long availableQty = onhandQty + hardPromisedQty;
//        /*
//         *
//         *  if availabilityQty < minThreshold then red
//         *  if availability > maxThreshold then green
//         *  else yellow (availabilityQty >= min and <= max)
//         *
//         * */
//        Threshold threshold = thresholdRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
//        System.out.println(threshold);
//        String stockLevel = "yellow";
//        if (availableQty < threshold.getMinThreshold()) {
//            stockLevel = "Red";
//        } else if (availableQty > threshold.getMaxThreshold()) {
//            stockLevel = "Green";
//        }

//        return new AvailabilityV3Response(itemId, locationId, availableQty, stockLevel);
    }
}
