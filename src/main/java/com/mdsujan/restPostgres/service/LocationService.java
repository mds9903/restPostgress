package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.exceptionHandling.*;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    DemandRepository demandRepository;


    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long locationId) throws Throwable {

        if (locationRepository.findById(locationId).isPresent()) {
            return locationRepository.findById(locationId).get();
        } else {
            throw new ResourceNotFoundException("no such location with given locationId; " +
                    "please enter correct locationId");
        }
    }

    public String deleteLocationById(Long locationId) throws Throwable {

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

    public Location createLocation(Location createLocationRequest) throws Throwable {

        // new record should not be created if record already exists

        // if record with same id exists then simply return it
        if (locationRepository.findById(createLocationRequest.getLocationId()).isPresent()) {
            throw new DuplicateResourceException("an location with same locationId already exists; " +
                    "please provide a unique locationId in the request body");
        }
        // else we create a new location

        return locationRepository.save(createLocationRequest);
    }

    public Location updateLocationPut(@Valid Long locationId, @Valid Location updateLocationRequest) throws Throwable {
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
            return updateLocationRequest;
        } else {
            throw new ResourceNotFoundException("cannot update this location; location not found; please enter a correct locationId");
        }
    }

    public Location updateLocationPatch(Long locationId, Location updateLocationRequest) throws Throwable {
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
