package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.request.CreateThresholdRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThresholdService {
    @Autowired
    ThresholdRepository thresholdRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    LocationRepository locationRepository;
    public List<Threshold> getAllThresholds() {
        return thresholdRepository.findAll();
    }

    public Threshold getThreshold(Long thresholdId) {
        return thresholdRepository.findById(thresholdId).get();
    }

//    public ThresholdDetailsResponse getThresholdDetailsByItemAndLocation(Long itemId, Long thresholdId) {
//        // seems a little hard coded; an efficient solution could be updated later
//        // get the list of thresholds with matching itemId and thresholdId
//        List<Threshold> thresholdList = getThresholdsByItemIdAndLocationId(itemId, thresholdId);
//        // from this list extract the sum of quantities for ONHAND and INTRANSIT thresholds
//        Long minThreshold = thresholdList.stream()
//                .filter(threshold -> threshold.getThresholdType() == AllowedThresholdTypes.PLANNED)
//                .map(Threshold::getThresholdQty)
//                .reduce(0L, (a, b) -> a + b);
//        Long maxThreshold = thresholdList.stream()
//                .filter(threshold -> threshold.getThresholdType() == AllowedThresholdTypes.HARD_PROMISED)
//                .map(Threshold::getThresholdQty)
//                .reduce(0L, (a, b) -> a + b);
//
////        System.out.println("\n\nOnhand: "+onhandQty+"\t\tIntransit: "+intransitQty);
//        // form a ThresholdDetailsResponse with these values
//        // return the ThresholdDetailsResponse
//        return new ThresholdDetailsResponse(itemId, thresholdId, new ThresholdDetails(minThreshold, maxThreshold));
//    }

    private List<Threshold> getThresholdsByItemIdAndLocationId(Long itemId, Long thresholdId) {
        return thresholdRepository.findByItemItemIdAndLocationLocationId(itemId, thresholdId);
    }


    public Threshold createThreshold(CreateThresholdRequest createThresholdRequest) {
        // create entry for given item/threshold pair
        // entry should not be made if entry with the given pair already exists
        Threshold threshold = new Threshold(createThresholdRequest);
        if (locationRepository.findById(createThresholdRequest.getLocationId()).isPresent()
                && itemRepository.findById(createThresholdRequest.getItemId()).isPresent()) {

            // get the item for this threshold
            Item item = itemRepository.findById(createThresholdRequest.getItemId()).get();
            // get the threshold for this threshold
            Location location = locationRepository.findById(createThresholdRequest.getLocationId()).get();
            threshold.setItem(item);
            threshold.setLocation(location);

            // save this new threshold
            threshold = thresholdRepository.save(threshold);
        }
        return threshold;
    }

    public Boolean deleteThreshold(Long thresholdId) {
        try {
            thresholdRepository.deleteById(thresholdId);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
