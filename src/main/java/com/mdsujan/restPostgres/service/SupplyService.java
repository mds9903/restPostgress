package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.SupplyDetails;
import com.mdsujan.restPostgres.response.SupplyDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SupplyService {

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    LocationRepository locationRepository;

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    public Supply getSupplyById(Long supplyId) {
        return supplyRepository.findById(supplyId).get();
    }

    public List<Supply> getSuppliesByItemIdAndLocationId(Long itemId, Long locationId) {
        return supplyRepository.findByItemItemIdAndLocationLocationId(itemId,locationId);
    }


    public List<Supply> getSuppliesByItemId(Long itemId) {
        return supplyRepository.findByItemItemId(itemId);
    }

    public SupplyDetailsResponse getSupplDetailsByItemAndLocation(Long itemId, Long locationId) {
        // seems a little hard coded; an efficient solution could be updated later
        // get the list of supplies with matching itemId and locationId
        List<Supply> supplyList = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
        // from this list extract the sum of quantities for ONHAND and INTRANSIT supplies
        Long onhandQty = supplyList.stream()
                .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.ONHAND)
                .map(Supply::getQty)
                .reduce(0L, (a, b) -> a + b);
        Long intransitQty = supplyList.stream()
                .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.INTRANSIT)
                .map(Supply::getQty)
                .reduce(0L, (a, b) -> a + b);

//        System.out.println("\n\nOnhand: "+onhandQty+"\t\tIntransit: "+intransitQty);
        // form a SupplyDetailsResponse with these values
        // return the SupplyDetailsResponse
        return new SupplyDetailsResponse(itemId, locationId, new SupplyDetails(onhandQty, intransitQty));
    }

    public Supply updateSupply(Long supplyId, UpdateSupplyRequest updateSupplyRequest) {
        // update a supply for given supplyId
        Supply supplyToUpdate = supplyRepository.findById(supplyId).get();
        // if there is no such supply of given supplyId
        if (!Objects.equals(supplyToUpdate.getId(), supplyId)) {
            // return the old supply as response
            return supplyToUpdate;
        }
        // else perform the update
        try {
            // as per given requirement
            // "update the existing supply qty"; need to confirm later
            if (updateSupplyRequest.getQty() != null) {
                supplyToUpdate.setQty(updateSupplyRequest.getQty());
            }
            // save the new supply to the db
            supplyToUpdate = supplyRepository.save(supplyToUpdate);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return supplyToUpdate;
    }

    public Supply createNewSupply(CreateSupplyRequest createSupplyRequest) {
        // create a supply for an item on a location (given in the request body)
        // if the itemId and the locationId are present in the items and locations table
        Supply supply = new Supply(createSupplyRequest);
        if (locationRepository.findById(createSupplyRequest.getLocationId()).isPresent()
                && itemRepository.findById(createSupplyRequest.getItemId()).isPresent()) {

            // get the item for this supply
            Item item = itemRepository.findById(createSupplyRequest.getItemId()).get();
            // get the location for this supply
            Location location = locationRepository.findById(createSupplyRequest.getLocationId()).get();
            supply.setItem(item);
            supply.setLocation(location);

            // save this new supply
            supply = supplyRepository.save(supply);
        }
        return supply;
        // then abort this create request
        // else
    }

    public Boolean deleteSupply(Long supplyId) {
        try {
            supplyRepository.deleteById(supplyId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
