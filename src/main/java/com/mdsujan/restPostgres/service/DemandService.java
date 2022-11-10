package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandService {
    @Autowired
    DemandRepository demandRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    LocationRepository locationRepository;
    public List<Demand> getAll() {
        return demandRepository.findAll();
    }

    public Demand getDemandById(Long demandId) {
        return demandRepository.findById(demandId).get();
    }

    public Demand createNewDemand(CreateDemandRequest createDemandRequest) {
        // create a demand for an item on a location (given in the request body)
        // if the itemId and the locationId are present in the items and locations table
        Demand demand = new Demand(createDemandRequest);
        if (locationRepository.findById(createDemandRequest.getLocationId()).isPresent()
                && locationRepository.findById(createDemandRequest.getItemId()).isPresent()) {

            // get the item for this demand
            Item item = itemRepository.findById(createDemandRequest.getItemId()).get();
            // get the location for this demand
            Location location = locationRepository.findById(createDemandRequest.getLocationId()).get();
            demand.setItem(item);
            demand.setLocation(location);

            // save this new demand
            demand = demandRepository.save(demand);

            return demand;
        } else {
            return demand;
        }
        // then abort this create request
        // else
    }
}
