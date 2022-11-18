package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.request.CreateThresholdRequest;
import com.mdsujan.restPostgres.request.UpdateThresholdRequest;
import com.mdsujan.restPostgres.response.ThresholdDetails;
import com.mdsujan.restPostgres.response.ThresholdDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public Threshold getThreshold(Long thresholdId) throws Throwable {
        if(thresholdRepository.findById(thresholdId).isPresent()){
            return thresholdRepository.findById(thresholdId).get();
        }else{
            throw new ResourceNotFoundException("threshold not found; please provide correct thresholdId");
        }
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

    public String deleteThresholdById(Long thresholdId) {
        try {
            // check if threshold exists
            if(thresholdRepository.findById(thresholdId).isPresent()){
                // delete the existing threshold
                thresholdRepository.deleteById(thresholdId);
                return "Threshold with thresholdId="+thresholdId+" successfully deleted.";
            }
            // else give proper message
            return "Invalid thresholdId: no such threshold present";
        } catch (Exception e) {
            return "EXCEPTION OCCURRED" + e;
        }
    }

    public Threshold updateThresholdPut(Long thresholdId, UpdateThresholdRequest updateThresholdRequest) {
        // this method updates a threshold for given thresholdId

        // find the threshold
        Threshold thresholdToUpdate = thresholdRepository.findById(thresholdId).get();

        try {
            // perform the update
            thresholdToUpdate.setMinThreshold(updateThresholdRequest.getMinThreshold());
            thresholdToUpdate.setMaxThreshold(updateThresholdRequest.getMaxThreshold());

            // save the new threshold to the db
            thresholdToUpdate = thresholdRepository.save(thresholdToUpdate);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        // return the old threshold as response
        return thresholdToUpdate;
    }

    public Threshold updateThresholdPatch(Long thresholdId, UpdateThresholdRequest updateThresholdRequest) {
        // this method updates a threshold for given thresholdId

        // if there exists such threshold of given thresholdId
        // find the threshold
        Threshold thresholdToUpdate = thresholdRepository.findById(thresholdId).get();

        try {
            // perform the update
            if(updateThresholdRequest.getMinThreshold() != null){
                thresholdToUpdate.setMinThreshold(updateThresholdRequest.getMinThreshold());
            }
            if(updateThresholdRequest.getMaxThreshold() != null){
                thresholdToUpdate.setMaxThreshold(updateThresholdRequest.getMaxThreshold());
            }
            // save the new threshold to the db
            thresholdToUpdate = thresholdRepository.save(thresholdToUpdate);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        // return the old threshold as response
        return thresholdToUpdate;
    }

    public ThresholdDetailsResponse getThresholdDetailsByItemAndLocation(Long itemId, Long locationId) {
        // get threshold details
        // for given itemId and locationId find the min and max thresholds
        Threshold threshold = thresholdRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
        return new ThresholdDetailsResponse(itemId, locationId, new ThresholdDetails(threshold.getMinThreshold(), threshold.getMaxThreshold()));
    }

//    public Threshold updateThresholdDetailsPut(Long itemId, Long locationId, UpdateThresholdRequest updateThresholdRequest) {
//        // this method updates all thresholds with same item and location
//
//        // find the threshold
//        Threshold thresholdToUpdate = thresholdRepository.findById(thresholdId).get();
//
//        try {
//            // perform the update
//            thresholdToUpdate.setMinThreshold(updateThresholdRequest.getMinThreshold());
//            thresholdToUpdate.setMaxThreshold(updateThresholdRequest.getMaxThreshold());
//
//            // save the new threshold to the db
//            thresholdToUpdate = thresholdRepository.save(thresholdToUpdate);
//        } catch (Exception e) {
//            System.out.println("Exception occurred: " + e.getMessage());
//        }
//        // return the old threshold as response
//        return thresholdToUpdate;
//    }
//
//    public Threshold updateThresholdDetailsPatch(Long itemId, Long locationId, UpdateThresholdRequest updateThresholdRequest) {
//        // this method updates a threshold for given thresholdId
//
//        // if there exists such threshold of given thresholdId
//        // find the threshold
//        Threshold thresholdToUpdate = thresholdRepository.findById(thresholdId).get();
//
//        try {
//            // perform the update
//            if(updateThresholdRequest.getMinThreshold() != null){
//                thresholdToUpdate.setMinThreshold(updateThresholdRequest.getMinThreshold());
//            }
//            if(updateThresholdRequest.getMaxThreshold() != null){
//                thresholdToUpdate.setMaxThreshold(updateThresholdRequest.getMaxThreshold());
//            }
//            // save the new threshold to the db
//            thresholdToUpdate = thresholdRepository.save(thresholdToUpdate);
//        } catch (Exception e) {
//            System.out.println("Exception occurred: " + e.getMessage());
//        }
//        // return the old threshold as response
//        return thresholdToUpdate;
//    }
}
