package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.request.CreateLocationRequest;
import com.mdsujan.restPostgres.request.UpdateLocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long locationId) {
        return locationRepository.findById(locationId).get();
    }

    public Boolean deleteLocationById(Long locationId) {
        try {
            locationRepository.deleteById(locationId);
            return true;
        } catch (Exception e) {
            System.out.println("******************* EXCEPTION" + e);
            return false;
        }
    }

    public Location createLocation(CreateLocationRequest createLocationRequest) {
        // new record should not be created if record already exists

        // if record with same id exists then simply return it
        if (locationRepository.findById(createLocationRequest.getId()).isPresent()) {
            return locationRepository.findById(createLocationRequest.getId()).get();
        }
        // else we create a new record
        Location location = new Location(createLocationRequest);
        locationRepository.save(location);
        return location;
    }

    public Location updateLocationPut(Long locationId, UpdateLocationRequest updateLocationRequest) {
        // find the record matching with the id
        Location locationToUpdate = locationRepository.findById(locationId).get();
        // "API must honor the locationId value passed in the input"
        // if the new item does not have id then we abort;

        try {
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
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return locationToUpdate;
    }

    public Location updateLocationPatch(Long locationId, UpdateLocationRequest updateLocationRequest) {
        // find the record matching with the id
        Location locationToUpdate = locationRepository.findById(locationId).get();
        // "API must honor the locationId value passed in the input"
        // if the new item does not have id then we abort;

        try {

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
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return locationToUpdate;
    }
}
