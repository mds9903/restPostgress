package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.exceptionHandling.DuplicateResourceException;
import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.request.CreateThresholdRequest;
import com.mdsujan.restPostgres.request.UpdateThresholdRequest;
import com.mdsujan.restPostgres.response.ThresholdDetails;
import com.mdsujan.restPostgres.response.ThresholdDetailsResponse;
import com.mdsujan.restPostgres.response.ThresholdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (thresholdRepository.findById(thresholdId).isPresent()) {
            return thresholdRepository.findById(thresholdId).get();
        } else {
            throw new ResourceNotFoundException("threshold not found; please provide correct thresholdId");
        }
    }

    public Threshold createThreshold(CreateThresholdRequest createThresholdRequest) throws Throwable {
        // create entry for given item/location pair
        if (thresholdRepository.findById(
                createThresholdRequest.getThresholdId()).isPresent()) {
            throw new DuplicateResourceException(
                    "threshold with given thresholdId already exists, please provide unique thresholdId");
        }
        // the item and location referenced by this to-be-created threshold must be valid; they
        // must exist in the
        // items and locations tables
        if (locationRepository.findById(createThresholdRequest.getLocationId()).isPresent() && itemRepository.findById(
                createThresholdRequest.getItemId()).isPresent()) {
            // entry should not be made only if the given item and location pair doesn't already
            // exist in the
            // thresholds table
            if (thresholdRepository.findByItemIdAndLocationId(createThresholdRequest.getItemId(),
                    createThresholdRequest.getLocationId()) != null) {
                throw new DuplicateResourceException(
                        "a threshold with given itemId and locationId pair already exists; please provide unique " +
                                "itemId and " +
                                "locationId threshold record to create");
            } else {
                // create new threshold
                Threshold threshold = new Threshold(createThresholdRequest);

                // save this new threshold
                threshold = thresholdRepository.save(threshold);
                return threshold;
            }
        } else {
            throw new ResourceNotFoundException(
                    "no item and/or location found for given itemId and locationId; please provide correct " +
                            "itemId and locationId in the request");
        }
    }

    public List<ThresholdResponse> createThresholds(
            List<CreateThresholdRequest> createThresholdRequests)
            throws Throwable {
        // for each threshold in thresholdsList
        // perform threshold creation
        // also handle validations and errors elegantly

        List<Threshold> thresholdsCreated = new ArrayList<>();

        for (CreateThresholdRequest threshold : createThresholdRequests) {
            thresholdsCreated.add(createThreshold(threshold));
        }

        return thresholdsCreated.stream().map((ThresholdResponse::new)).collect(Collectors.toList());
    }

    public Threshold updateThresholdPut(Long thresholdId, UpdateThresholdRequest updateThresholdRequest)
            throws Throwable {
        // this method updates a threshold for given thresholdId

        // find the threshold
        if (thresholdRepository.findById(thresholdId).isPresent()) {
            Threshold thresholdToUpdate = thresholdRepository.findById(thresholdId).get();
            // perform the put update
            thresholdToUpdate.setMinThreshold(updateThresholdRequest.getMinThreshold());
            thresholdToUpdate.setMaxThreshold(updateThresholdRequest.getMaxThreshold());
            // save the new threshold to the db
            thresholdToUpdate = thresholdRepository.save(thresholdToUpdate);
            return thresholdToUpdate;
        } else {
            throw new ResourceNotFoundException(
                    "cannot update;no such threshold found; please provide correct thresholdId");
        }
    }

    public Threshold updateThresholdPatch(Long thresholdId, UpdateThresholdRequest updateThresholdRequest)
            throws Throwable {
        // this method updates a threshold for given thresholdId

        // find the threshold
        if (thresholdRepository.findById(thresholdId).isPresent()) {
            Threshold thresholdToUpdate = thresholdRepository.findById(thresholdId).get();
            // perform the path update
            if (updateThresholdRequest.getMinThreshold() != null) {
                thresholdToUpdate.setMinThreshold(updateThresholdRequest.getMinThreshold());
            }
            if (updateThresholdRequest.getMaxThreshold() != null) {
                thresholdToUpdate.setMaxThreshold(updateThresholdRequest.getMaxThreshold());
            }
            // save the new threshold to the db
            thresholdToUpdate = thresholdRepository.save(thresholdToUpdate);
            return thresholdToUpdate;
        } else {
            throw new ResourceNotFoundException(
                    "cannot update;no such threshold found; please provide correct thresholdId");
        }
    }

    public ThresholdDetailsResponse getThresholdDetailsByItemAndLocation(Long itemId, Long locationId) {
        // get threshold details
        // for given itemId and locationId find the min and max thresholds
        Threshold threshold = thresholdRepository.findByItemIdAndLocationId(itemId, locationId);
        return new ThresholdDetailsResponse(itemId, locationId, new ThresholdDetails(threshold.getMinThreshold(),
                threshold.getMaxThreshold()));
    }

    public String deleteThresholdById(Long thresholdId) throws Throwable {
        // check if threshold exists
        if (thresholdRepository.findById(thresholdId).isPresent()) {
            // delete the existing threshold
            thresholdRepository.deleteById(thresholdId);
            return "Threshold with thresholdId=" + thresholdId + " successfully deleted.";
        }
        throw new ResourceNotFoundException(
                "cannot delete; no such threshold found; please provide correct thresholdId");
    }

}
