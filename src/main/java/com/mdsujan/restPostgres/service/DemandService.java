package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.*;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.request.UpdateDemandRequest;
import com.mdsujan.restPostgres.response.DemandDetails;
import com.mdsujan.restPostgres.response.DemandDetailsResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public List<Demand> getDemandsByItemIdAndLocationId(Long itemId, Long locationId) {
        return demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
    }

    public DemandDetailsResponse getDemandDetailsByItemAndLocation(Long itemId, Long locationId) {
        // seems a little hard coded; an efficient solution could be updated later
        // get the list of demands with matching itemId and locationId
        List<Demand> demandList = getDemandsByItemIdAndLocationId(itemId, locationId);
        // from this list extract the sum of quantities for ONHAND and INTRANSIT demands
        Long plannedQty = demandList.stream()
                .filter(demand -> demand.getDemandType() == AllowedDemandTypes.PLANNED)
                .map(Demand::getDemandQty)
                .reduce(0L, (a, b) -> a + b);
        Long hardPromisedQty = demandList.stream()
                .filter(demand -> demand.getDemandType() == AllowedDemandTypes.HARD_PROMISED)
                .map(Demand::getDemandQty)
                .reduce(0L, (a, b) -> a + b);

//        System.out.println("\n\nOnhand: "+onhandQty+"\t\tIntransit: "+intransitQty);
        // form a DemandDetailsResponse with these values
        // return the DemandDetailsResponse
        return new DemandDetailsResponse(itemId, locationId, new DemandDetails(plannedQty, hardPromisedQty));
    }

    public Demand updateDemand(Long demandId, UpdateDemandRequest updateDemandRequest) {
        // update a demand for given demandId
        Demand demandToUpdate = demandRepository.findById(demandId).get();
        // if there is no such demand of given demandId
        if (!Objects.equals(demandToUpdate.getDemandId(), demandId)) {
            // return the old demand as response
            return demandToUpdate;
        }
        // else perform the update
        try {
            // as per given requirement
            // "update the existing demand qty"; need to confirm later
            if (updateDemandRequest.getQty() != null) {
                demandToUpdate.setDemandQty((updateDemandRequest.getQty()));
            }
            // save the new demand to the db
            demandToUpdate = demandRepository.save(demandToUpdate);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return demandToUpdate;
    }
    public Boolean deleteDemand(Long demandId) {
        try {
            demandRepository.deleteById(demandId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
