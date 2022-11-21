package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.exceptionHandling.*;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateLocationRequest;
import com.mdsujan.restPostgres.request.UpdateItemRequest;
import com.mdsujan.restPostgres.request.UpdateLocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    DemandRepository demandRepository;

    // Validator to validate programmatically
    private final Validator validator;

    public LocationService(Validator validator) {
        this.validator = validator;
    }

    private void validateUpdateLocationRequest(UpdateLocationRequest updateLocationRequest) throws Throwable {
        // validates the request body to have all the fields are there; for PUT methods
        Set<ConstraintViolation<UpdateLocationRequest>> violationSet = validator.validate(updateLocationRequest);
        if (!violationSet.isEmpty()) {
            throw new UpdateResourceRequestBodyInvalidException("some of the fields in the request body is missing; " +
                    "please send full request body for updating location");
        }
    }

    private void validateLocationId(Long locationId) throws Throwable {
        // validates the itemId (passed in path-var)
        Set<ConstraintViolation<Long>> violationSet = validator.validate(locationId);
        if (!violationSet.isEmpty()) {
            throw new InvalidResourceIdException("locationId not valid; please provide a valid locationId of type Long");
        }
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long locationId) throws Throwable {
        validateLocationId(locationId);
        if (locationRepository.findById(locationId).isPresent()) {
            return locationRepository.findById(locationId).get();
        } else {
            throw new ResourceNotFoundException("no such location with given locationId; " +
                    "please enter correct locationId");
        }
    }

    public String deleteLocationById(Long locationId) throws Throwable {
        validateLocationId(locationId);
        // check if item exists
        if (locationRepository.findById(locationId).isPresent()) {
            // if any child records depend on this location
            if (supplyRepository.findByLocationLocationId(locationId).size() > 0
                    || demandRepository.findByLocationLocationId(locationId).size() > 0) {
                // this location cannot be deleted
//                return "Cannot delete location; it has child records";
                throw new ResourceConflictException("this location cannot be deleted; has child dependencies");
            }
            // else delete the existing location
            locationRepository.deleteById(locationId);
            return "Location with locationId=" + locationId + " successfully deleted.";
        }
        // else give proper message
        throw new ResourceNotFoundException("cannot delete; no such location found");
    }

    public Location createLocation(CreateLocationRequest createLocationRequest) throws Throwable {

        // new record should not be created if record already exists

        // if record with same id exists then simply return it
        if (locationRepository.findById(createLocationRequest.getId()).isPresent()) {
            throw new DuplicateResourceException("an location with same locationId already exists; " +
                    "please provide a unique locationId in the request body");
        }
        // else we create a new location
        Location location = new Location(createLocationRequest);
        location = locationRepository.save(location);
        return location;
    }

    public Location updateLocationPut(Long locationId, UpdateLocationRequest updateLocationRequest) throws Throwable {

        // perform validations

        // throws exception for when the request body is missing fields
        // for a put method the complete request body with all required fields must be present
        validateUpdateLocationRequest(updateLocationRequest);

        // throws an exception for when the locationId is not of correct type; ie, Long
        validateLocationId(locationId);

        // proceed with update
        if (!locationId.equals(updateLocationRequest.getLocationId())) {
            // locationId in the body should match the locationId in the path variable
            throw new UpdateResourceRequestBodyInvalidException("locationId in the body is not matching the locationId in the path variable; " +
                    "please provide the right locationId");
        }
        if (locationRepository.findById(locationId).isPresent()) {

            // find the record matching with the id
            Location locationToUpdate = locationRepository.findById(locationId).get();

            // update the locationToUpdate
            locationToUpdate.setLocationDesc(updateLocationRequest.getLocationDesc());
            locationToUpdate.setType(updateLocationRequest.getType());
            locationToUpdate.setPickupAllowed(updateLocationRequest.getPickupAllowed());
            locationToUpdate.setShippingAllowed(updateLocationRequest.getShippingAllowed());
            locationToUpdate.setDeliveryAllowed(updateLocationRequest.getDeliveryAllowed());
            locationToUpdate.setAddrLine1(updateLocationRequest.getAddrLine1());
            locationToUpdate.setAddrLine2(updateLocationRequest.getAddrLine2());
            locationToUpdate.setAddrLine3(updateLocationRequest.getAddrLine3());
            locationToUpdate.setCity(updateLocationRequest.getCity());
            locationToUpdate.setState(updateLocationRequest.getState());
            locationToUpdate.setCountry(updateLocationRequest.getCountry());
            locationToUpdate.setPincode(updateLocationRequest.getPincode());

            // save the new entity
            locationToUpdate = locationRepository.save(locationToUpdate);
            // return the updated location as response
            return locationToUpdate;
        } else {
            throw new ResourceNotFoundException("cannot update this location; location not found; please enter a correct locationId");
        }
    }

    public Location updateLocationPatch(Long locationId, UpdateLocationRequest updateLocationRequest) throws Throwable {
        // validate locationId for type
        validateLocationId(locationId);

        if (!locationId.equals(updateLocationRequest.getLocationId())) {
            // itemId in the body is not matching the itemId in the path variable
            throw new UpdateResourceRequestBodyInvalidException("itemId in the body is not matching the itemId in the path variable; " +
                    "please provide the right itemId to avoid confusion");
        }
        if (locationRepository.findById(locationId).isPresent()) {
            // find the record matching with the id
            Location locationToUpdate = locationRepository.findById(locationId).get();

            // update the locationToUpdate
            if (updateLocationRequest.getLocationDesc() != null && !updateLocationRequest.getLocationDesc().isEmpty()) {
                locationToUpdate.setLocationDesc(updateLocationRequest.getLocationDesc());
            }

            if (updateLocationRequest.getType() != null && !updateLocationRequest.getType().isEmpty()) {
                locationToUpdate.setType(updateLocationRequest.getType());
            }
            if (updateLocationRequest.getPickupAllowed() != null) {
                locationToUpdate.setPickupAllowed(updateLocationRequest.getPickupAllowed());
            }
            if (updateLocationRequest.getShippingAllowed() != null) {
                locationToUpdate.setShippingAllowed(updateLocationRequest.getShippingAllowed());
            }
            if (updateLocationRequest.getDeliveryAllowed() != null) {
                locationToUpdate.setDeliveryAllowed(updateLocationRequest.getDeliveryAllowed());
            }
            if (updateLocationRequest.getAddrLine1() != null && !updateLocationRequest.getAddrLine1().isEmpty()) {
                locationToUpdate.setAddrLine1(updateLocationRequest.getAddrLine1());
            }
            if (updateLocationRequest.getAddrLine2() != null && !updateLocationRequest.getAddrLine2().isEmpty()) {
                locationToUpdate.setAddrLine2(updateLocationRequest.getAddrLine2());
            }
            if (updateLocationRequest.getAddrLine3() != null && !updateLocationRequest.getAddrLine3().isEmpty()) {
                locationToUpdate.setAddrLine3(updateLocationRequest.getAddrLine3());
            }
            if (updateLocationRequest.getCity() != null && !updateLocationRequest.getCity().isEmpty()) {
                locationToUpdate.setCity(updateLocationRequest.getCity());
            }
            if (updateLocationRequest.getState() != null && !updateLocationRequest.getState().isEmpty()) {
                locationToUpdate.setState(updateLocationRequest.getState());
            }
            if (updateLocationRequest.getCountry() != null && !updateLocationRequest.getCountry().isEmpty()) {
                locationToUpdate.setCountry(updateLocationRequest.getCountry());
            }

            // save the new entity
            locationToUpdate = locationRepository.save(locationToUpdate);
            // return the updated location as response
            return locationToUpdate;
        } else {
            throw new ResourceNotFoundException("cannot update this location; location not found; please enter a correct locationId");
        }
    }
}
