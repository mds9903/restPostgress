package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.exceptionHandling.*;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.SupplyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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

    public Supply getSupplyById(Long supplyId) throws Throwable {
//        return supplyRepository.findById(supplyId).get();
        if (supplyRepository.findById(supplyId).isPresent()) {
            return supplyRepository.findById(supplyId).get();
        } else {
            throw new ResourceNotFoundException("no supply found for given supplyId; " + "please enter correct supplyId");
        }
    }

//    public SupplyDetailsResponse getSupplyDetailsByItemAndLocation(Long itemId, Long locationId) throws Throwable {
//        List<Supply> supplyList = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
//        if (supplyList != null && supplyList.size() > 0) {
//            // from this list extract the sum of quantities for ONHAND and INTRANSIT supplies
//            Long onhandQty = supplyList.stream()
//                    .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.ONHAND)
//                    .map(Supply::getSupplyQty)
//                    .reduce(0L, Long::sum);
//            Long intransitQty = supplyList.stream()
//                    .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.INTRANSIT)
//                    .map(Supply::getSupplyQty)
//                    .reduce(0L, Long::sum);
//
//            return new SupplyDetailsResponse(itemId, locationId, new SupplyDetails(onhandQty, intransitQty));
//        } else {
//            throw new ResourceNotFoundException("no supplies found for given itemId and locationId;" +
//                    "please give correct itemId and/or locationId");
//        }
//    }

    public Supply createNewSupply(CreateSupplyRequest createSupplyRequest) throws Throwable {
        // create a supply for an item on a location (given in the request body)

        // if the itemId and the locationId are valid
        if (locationRepository.findById(createSupplyRequest.getLocationId()).isPresent() && itemRepository.findById(createSupplyRequest.getItemId()).isPresent()) {
            // create the supply
            Supply supply = new Supply(createSupplyRequest);
            // save this new supply
            supply = supplyRepository.save(supply);
            return supply;
        } else {
            throw new CreateResourceOperationNotAllowed("there are no items and locations for your requested create supply; " + "please provide an item id and a location id that exists");
        }
    }

    public List<SupplyResponse> createNewSupplies(List<CreateSupplyRequest> createSupplyRequests) throws DuplicateResourceException {

        // for each supply in supplyList
        // perform supply creation
        // also handle validations and errors elegantly
        List<Supply> suppliesCreated = new ArrayList<>();

        for (CreateSupplyRequest supply : createSupplyRequests) {
            try {
                suppliesCreated.add(createNewSupply(supply));
            } catch (Throwable e) {
                throw new DuplicateResourceException("For supply #" + (createSupplyRequests.indexOf(supply) + 1) + "; an supply with same supplyId already exists; please provide a unique supplyId in the request body");
            }
        }

        return suppliesCreated.stream().map((SupplyResponse::new)).collect(Collectors.toList());
    }


    public Supply updateSupplyPut(Long supplyId, UpdateSupplyRequest updateSupplyRequest) throws Throwable {
        // PUT Update the existing supply qty
        if (supplyRepository.findById(supplyId).isPresent()) {
            // update a supply for given supplyId
            Supply supplyToUpdate = supplyRepository.findById(supplyId).get();
            supplyToUpdate.setSupplyQty(updateSupplyRequest.getSupplyQty());
            // save the new supply to the db
//            return supplyRepository.save(supplyToUpdate);
            supplyToUpdate = supplyRepository.save(supplyToUpdate);
            return supplyToUpdate;
        } else {
            throw new ResourceNotFoundException("cannot update supply; supply not found with given supplyId; " + "please provide correct supplyId");
        }
    }

    public Supply updateSupplyPatch(Long supplyId, UpdateSupplyRequest updateSupplyRequest) throws Throwable {
        // PATCH Update the existing supply qty
        if (supplyRepository.findById(supplyId).isPresent()) {
            // update a supply for given supplyId
            Supply supplyToUpdate = supplyRepository.findById(supplyId).get();
            if (updateSupplyRequest.getSupplyQty() != null) {
                supplyToUpdate.setSupplyQty(updateSupplyRequest.getSupplyQty());
            }
            // save the new supply to the db
            return supplyRepository.save(supplyToUpdate);
        } else {
            throw new ResourceNotFoundException("cannot update supply; supply not found with given supplyId; " + "please provide correct supplyId");
        }
    }

    public String deleteSupply(Long supplyId) throws Throwable {
        if (supplyRepository.findById(supplyId).isPresent()) {
            supplyRepository.deleteById(supplyId);
            return "supply with supplyId = '" + supplyId + "' deleted successfully";
        } else {
            throw new ResourceNotFoundException("cannot delete; supply not found");
        }
    }


}
