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
import com.mdsujan.restPostgres.response.AvailabilityV1Response;
import com.mdsujan.restPostgres.response.AvailabilityV2Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/availability")
public class AvailabilityV2Controller {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    DemandRepository demandRepository;

    @Autowired
    ThresholdRepository thresholdRepository;

    @GetMapping("/{itemId}/{locationId}")
    public AvailabilityV2Response getAvailabilityStockLevel(@PathVariable Long itemId, @PathVariable Long locationId) {
        // get the availabilityQty for given itemId and locationId
        Long onhandQty = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId)
                .stream()
                .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.ONHAND)
                .map(Supply::getSupplyQty)
                .reduce(0L, Long::sum);
        // get the hardPromised qty for the demand of the given item and location
        Long hardPromisedQty = demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId)
                .stream()
                .filter(demand -> demand.getDemandType() == AllowedDemandTypes.HARD_PROMISED)
                .map(Demand::getDemandQty)
                .reduce(0L, Long::sum);

        Long availableQty = onhandQty + hardPromisedQty;
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

        return new AvailabilityV2Response(itemId, locationId, availableQty, stockLevel);
    }
}
