package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.response.AvailabilityV1Response;
import com.mdsujan.restPostgres.response.AvailabilityV2Response;
import com.mdsujan.restPostgres.response.AvailabilityV3Response;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {

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


    public AvailabilityV1Response getAvlQtyByItemAndLocationV1(Long itemId, Long locationId) throws Throwable {
        // sample response
        /*
         * {
         *   "itemId":123,
         *   "locationId": 456,
         *   "availableQty": 5
         * }
         * availableQty = supplyQty - demandQty
         * Note: consider only ONHAND supply and HARD_PROMISED demand
         * */

        List<Supply> supplyList = supplyRepository.findByItemItemIdAndLocationLocationId(
                itemId, locationId);

        List<Demand> demandList = demandRepository.findByItemItemIdAndLocationLocationId(
                itemId, locationId);

        if (supplyList == null || demandList == null) {
            throw new ResourceNotFoundException("no supplies for this itemId and locationId; " +
                    "please enter correct itemId and locationId");
        }

        // gets only the "ONHAND" qty as mentioned in the assignment sheet
        Long onhandQty = supplyList
                .stream()
                .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.ONHAND)
                .map(Supply::getSupplyId)
                .reduce(0L, Long::sum);

        // get the "HARD_PROMISED" qty as mentioned in the assignment sheet
        Long hardPromisedQty = demandList
                .stream()
                .filter(demand -> demand.getDemandType() == AllowedDemandTypes.HARD_PROMISED)
                .map(Demand::getDemandQty)
                .reduce(0L, Long::sum);

        Long availableQty = onhandQty + hardPromisedQty;

        return new AvailabilityV1Response(itemId, locationId, availableQty);
    }

    public AvailabilityV1Response getAvlQtyByItemV1(Long itemId) throws Throwable {
        // sample response
        /*
         * {
         *   "itemId":123,
         *   "locationId": "NETWORK",
         *   "availableQty": 5
         * }
         *
         * availableQty = supplyQty - demandQty
         * Note: consider only ONHAND supply and HARD_PROMISED demand
         * */

        List<Supply> supplyList = supplyRepository.findByItemItemId(itemId);

        List<Demand> demandList = demandRepository.findByItemItemId(itemId);

        if (supplyList == null || demandList == null) {
            throw new ResourceNotFoundException("no supplies and/or demands for this itemId and locationId; " +
                    "please enter correct itemId and locationId");
        }

        // get the onhand qty for the supply of given item on all locations
        Long onhandQty = supplyList.stream()
                .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.ONHAND)
                .map(Supply::getSupplyId)
                .reduce(0L, Long::sum);

        // get the hardPromised qty for the demand of the given item on all locations
        Long hardPromisedQty = demandList.stream() // declarative programming
                .filter(demand -> demand.getDemandType() == AllowedDemandTypes.HARD_PROMISED)
                .map(Demand::getDemandQty)
                .reduce(0L, Long::sum);
        // using method reference and lambda (java 8+)

//        // imperative programming
//        Long sum = 0L;
//        for (Demand demand : demandList) {
//            if (demand.getDemandType() == AllowedDemandTypes.HARD_PROMISED) {
//                sum += demand.getDemandQty();
//            }
//        }
//        Long hardPromisedQty = sum;

        Long availableQty = onhandQty + hardPromisedQty;

        // "NETWORK" denotes all locations that have the same item
        return new AvailabilityV1Response(itemId, "NETWORK", availableQty);

    }

    public AvailabilityV2Response getStockLevelV2(Long itemId, Long locationId) throws ResourceNotFoundException {
        // get the stock level based on the availabilityQty for given itemId and locationId

        /*
         *
         *  if availabilityQty < minThreshold then red
         *  if availability > maxThreshold then green
         *  else yellow (availabilityQty >= min and <= max)
         *
         * */

        // get the hardPromised qty for the demand of the given item and location
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

        // qty available
        Long availableQty = onhandQty + hardPromisedQty;

        // threshold with given item and location to compare stock level
        Threshold threshold = thresholdRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);

        if (threshold == null) {
            throw new ResourceNotFoundException("no threshold found for given item and location");
        }

        // derive the stock level
        String stockLevel = "yellow";
        if (availableQty < threshold.getMinThreshold()) {
            stockLevel = "red";
        } else if (availableQty > threshold.getMaxThreshold()) {
            stockLevel = "green";
        }

        return new AvailabilityV2Response(itemId, locationId, availableQty, stockLevel);
    }

    public AvailabilityV3Response getStockLevelV3(Long itemId, Long locationId) {
        // get the stock level based on the availabilityQty for given itemId and locationId
        // based from the values given in the properties file
        // here extracted using @Value supplyTypes and demandTypes
        List<Supply> supplyList = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
        Long supplyQty = 0L;
        List<String> supplyTypeList = List.of(supplyTypes); // get the supply types from the config

        List<Demand> demandList = demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
        Long demandQty = 0L;
        List<String> demandTypeList = List.of(demandTypes);  // get the supply types from the config

        System.out.printf("supplyTypeList : " + supplyTypeList);
        System.out.printf("demandTypeList : " + demandTypeList);

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

