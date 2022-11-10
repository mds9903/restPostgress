package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.response.SupplyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Supply createNewSupply(CreateSupplyRequest createSupplyRequest) {
        // create a supply for an item on a location (given in the request body)
        // if the itemId and the locationId are present in the items and locations table
        Supply supply = new Supply(createSupplyRequest);
        if (locationRepository.findById(createSupplyRequest.getLocationId()).isPresent()
                && locationRepository.findById(createSupplyRequest.getItemId()).isPresent()) {

            // get the item for this supply
            Item item = itemRepository.findById(createSupplyRequest.getItemId()).get();
            // get the location for this supply
            Location location = locationRepository.findById(createSupplyRequest.getLocationId()).get();
            supply.setItem(item);
            supply.setLocation(location);

            // save this new supply
            supply = supplyRepository.save(supply);

            return supply;
        } else {
            return supply;
        }
        // then abort this create request
        // else
    }
}
