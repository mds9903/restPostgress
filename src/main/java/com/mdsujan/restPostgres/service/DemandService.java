package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.*;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.exceptionHandling.CreateResourceOperationNotAllowed;
import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.exceptionHandling.UpdateResourceRequestBodyInvalidException;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.request.UpdateDemandRequest;
import com.mdsujan.restPostgres.response.DemandDetails;
import com.mdsujan.restPostgres.response.DemandDetailsResponse;

import com.mdsujan.restPostgres.response.DemandDetails;
import com.mdsujan.restPostgres.response.DemandDetailsResponse;
import com.sun.jdi.request.DuplicateRequestException;
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

    public Demand getDemandById(Long demand) throws Throwable {
        if (demandRepository.findById(demand).isPresent()) {
            return demandRepository.findById(demand).get();
        } else {
            throw new ResourceNotFoundException("no demand found for given demandId; " +
                    "please enter correct demandId");
        }
    }

    public DemandDetailsResponse getDemandDetailsByItemAndLocation(Long itemId, Long locationId) throws Throwable {
        List<Demand> demandList = demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
        if (demandList != null && demandList.size() > 0) {
            // from this list extract the sum of quantities for ONHAND and INTRANSIT supplies
            Long planned = demandList.stream()
                    .filter(demand -> demand.getDemandType() == AllowedDemandTypes.PLANNED)
                    .map(Demand::getDemandQty)
                    .reduce(0L, Long::sum);
            Long hardPromised = demandList.stream()
                    .filter(demand -> demand.getDemandType() == AllowedDemandTypes.HARD_PROMISED)
                    .map(Demand::getDemandQty)
                    .reduce(0L, Long::sum);

            return new DemandDetailsResponse(itemId, locationId, new DemandDetails(planned, hardPromised));
        } else {
            throw new ResourceNotFoundException("no supplies found for given itemId and locationId;" +
                    "please give correct itemId and/or locationId");
        }
    }

    public List<Demand> getDemandsByItemIdAndLocationId(Long itemId, Long locationId) {
        return demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
    }

    public String deleteDemand(Long demandId) throws Throwable {
        if (demandRepository.findById(demandId).isPresent()) {
            demandRepository.deleteById(demandId);
            return "demand with demandId = '" + demandId + "' deleted successfully";
        } else {
            throw new ResourceNotFoundException("cannot delete; no demand found with given demandId;" +
                    "please provide correct demandId");
        }
    }

    public List<Demand> getDemandsByItemId(Long itemId) {
        return demandRepository.findByItemItemId(itemId);
    }

    public Demand createNewDemand(CreateDemandRequest createDemandRequest) throws Throwable {
        // create a demand for an item on a location (given in the request body)

        // if the itemId and the locationId are present in the items and locations table
        if (locationRepository.findById(createDemandRequest.getLocationId()).isPresent()
                && itemRepository.findById(createDemandRequest.getItemId()).isPresent()) {
            // create the demand
            Demand demand = new Demand(createDemandRequest);
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
            throw new CreateResourceOperationNotAllowed("there are no items and locations for your requested create demand; " +
                    "please provide an item id and a location id that exists");
        }
    }

    public Demand updateDemandPut(Long demandId, UpdateDemandRequest updateDemandRequest) throws Throwable {
        if (demandRepository.findById(demandId).isPresent()) {
            if (updateDemandRequest.getDemandType() == null
                    || updateDemandRequest.getDemandQty() == null) {
                throw new UpdateResourceRequestBodyInvalidException("some fields of the update demand request are missing;" +
                        " please provide all the fields for a demand resource to do an update");
            } else {
                // update a demand for given demandId
                Demand demandToUpdate = demandRepository.findById(demandId).get();
                demandToUpdate.setDemandQty(updateDemandRequest.getDemandQty());
                // save the new demand to the db
                demandToUpdate = demandRepository.save(demandToUpdate);
                return demandToUpdate;
            }
        } else {
            throw new ResourceNotFoundException("cannot update demand; demand not found with given demandId; " +
                    "please provide correct demandId");
        }
    }

    public Demand updateDemandPatch(Long demandId, UpdateDemandRequest updateDemandRequest) throws Throwable {
        if (demandRepository.findById(demandId).isPresent()) {
            // update a demand for given demandId
            Demand demandToUpdate = demandRepository.findById(demandId).get();
            if (updateDemandRequest.getDemandQty() != null) {
                demandToUpdate.setDemandQty(updateDemandRequest.getDemandQty());
            }
            // save the new demand to the db
            demandToUpdate = demandRepository.save(demandToUpdate);
            return demandToUpdate;
        } else {
            throw new ResourceNotFoundException("cannot update demand; demand not found with given demandId; " +
                    "please provide correct demandId");
        }
    }
}
