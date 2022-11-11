package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.response.AvailabilityV1Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/availability")
public class AvailabilityV1Controller {
    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    DemandRepository demandRepository;

    @GetMapping("/{itemId}/{locationId}") // get availability qty of item at a given location
    public AvailabilityV1Response getAvailabilityQty(@PathVariable Long itemId, @PathVariable Long locationId){
        /*sample response
         * {
         *   "itemId":123,
         *   "locationId": 456,
         *   "availableQty": 5
         * }
         *
         * availableQty = supplyQty - demandQty
         * Note: consider only ONHAND supply and HARD_PROMISED demand
         * */
        // get the onhand qty for the supply of given item and location
        Long onhandQty = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId)
                .stream()
                .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.ONHAND)
                .map(Supply::getQty)
                .reduce(0L, (a, b) -> a + b);
        // get the hardPromised qty for the demand of the given item and location
        Long hardPromisedQty = demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId)
                .stream()
                .filter(demand -> demand.getDemandType() == AllowedDemandTypes.HARD_PROMISED)
                .map(Demand::getDemandQty)
                .reduce(0L, (a, b) -> a + b);

        Long availableQty = onhandQty + hardPromisedQty;

        return new AvailabilityV1Response(itemId, locationId, availableQty);
    }

    @GetMapping("/{itemId}") // get availability qty of item in all locations (total)
    public AvailabilityV1Response getAllLocationAvailabilityQty(@PathVariable Long itemId){
        /*sample response
         * {
         *   "itemId":123,
         *   "locationId": "NETWORK",
         *   "availableQty": 5
         * }
         *
         * availableQty = supplyQty - demandQty
         * Note: consider only ONHAND supply and HARD_PROMISED demand
         * */
        // get the onhand qty for the supply of given item on all locations
        Long onhandQty = supplyRepository.findByItemItemId(itemId)
                .stream()
                .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.ONHAND)
                .map(Supply::getQty)
                .reduce(0L, Long::sum);
        // get the hardPromised qty for the demand of the given item on all locations
        Long hardPromisedQty = demandRepository.findByItemItemId(itemId)
                .stream()
                .filter(demand -> demand.getDemandType() == AllowedDemandTypes.HARD_PROMISED)
                .map(Demand::getDemandQty)
                .reduce(0L, Long::sum);

        Long availableQty = onhandQty + hardPromisedQty;

        return new AvailabilityV1Response(itemId, "NETWORK", availableQty);
    }
}
