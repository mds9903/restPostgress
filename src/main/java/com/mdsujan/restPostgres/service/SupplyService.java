package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.exceptionHandling.*;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateLocationRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.DemandDetailsResponse;
import com.mdsujan.restPostgres.response.SupplyDetails;
import com.mdsujan.restPostgres.response.SupplyDetailsResponse;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
            throw new ResourceNotFoundException("no supply found for given supplyId; " +
                    "please enter correct supplyId");
        }
    }

//    public List<Supply> getSuppliesByItemIdAndLocationId(Long itemId, Long locationId) {
//        List<Supply> supplyList = supplyRepository.findByItemItemIdAndLocationLocationId(itemId,locationId);
//        if(supplyList != null && supplyList.size() > 0){
//            return supplyList;
//        }else{
//            throw new ResourceAccessException("no supplies found for given itemId and locationId;" +
//                    " please give correct itemId and/or locationId");
//        }
//    }

//    public List<Supply> getSuppliesByItemId(Long itemId) {
//        return supplyRepository.findByItemItemId(itemId);
//    }

    public SupplyDetailsResponse getSupplyDetailsByItemAndLocation(Long itemId, Long locationId) throws Throwable {
        List<Supply> supplyList = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
        if (supplyList != null && supplyList.size() > 0) {
            // from this list extract the sum of quantities for ONHAND and INTRANSIT supplies
            Long onhandQty = supplyList.stream()
                    .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.ONHAND)
                    .map(Supply::getSupplyQty)
                    .reduce(0L, Long::sum);
            Long intransitQty = supplyList.stream()
                    .filter(supply -> supply.getSupplyType() == AllowedSupplyTypes.INTRANSIT)
                    .map(Supply::getSupplyQty)
                    .reduce(0L, Long::sum);

            return new SupplyDetailsResponse(itemId, locationId, new SupplyDetails(onhandQty, intransitQty));
        } else {
            throw new ResourceNotFoundException("no supplies found for given itemId and locationId;" +
                    "please give correct itemId and/or locationId");
        }
    }

    public List<Supply> getSupplyDetailsBySupplyTypeAndLocation(Long supplyType, Long locationId) {
        return supplyRepository.findBySupplyTypeAndLocationLocationId(supplyType, locationId);
    }


    public Supply updateSupplyPut(Long supplyId, UpdateSupplyRequest updateSupplyRequest) throws Throwable {
        // PUT Update the existing supply qty
        if (!Objects.equals(updateSupplyRequest.getSupplyId(), supplyId)) {
            throw new UpdateResourceRequestBodyInvalidException("supplyId in the body is not matching the supplyId in the path variable; " +
                    "please provide the right supplyId");
        }
        if (supplyRepository.findById(supplyId).isPresent()) {
            // update a supply for given supplyId
            Supply supplyToUpdate = supplyRepository.findById(supplyId).get();
            supplyToUpdate.setSupplyQty(updateSupplyRequest.getSupplyQty());
            // save the new supply to the db
            supplyToUpdate = supplyRepository.save(supplyToUpdate);
            return supplyToUpdate;
        } else {
            throw new ResourceNotFoundException("cannot update supply; supply not found with given supplyId; " +
                    "please provide correct supplyId");
        }
    }

    public Supply updateSupplyPatch(Long supplyId, UpdateSupplyRequest updateSupplyRequest) throws Throwable {
        // PATCH Update the existing supply qty
        if (!Objects.equals(updateSupplyRequest.getSupplyId(), supplyId)) {
            throw new UpdateResourceRequestBodyInvalidException("supplyId in the body is not matching the supplyId in the path variable; " +
                    "please provide the right supplyId");
        }
        if (supplyRepository.findById(supplyId).isPresent()) {
            // update a supply for given supplyId
            Supply supplyToUpdate = supplyRepository.findById(supplyId).get();
            if (updateSupplyRequest.getSupplyQty() != null) {
                supplyToUpdate.setSupplyId(updateSupplyRequest.getSupplyQty());
            }
            // save the new supply to the db
            supplyToUpdate = supplyRepository.save(supplyToUpdate);
            return supplyToUpdate;
        } else {
            throw new ResourceNotFoundException("cannot update supply; supply not found with given supplyId; " +
                    "please provide correct supplyId");
        }
    }

    public Supply createNewSupply(CreateSupplyRequest createSupplyRequest) throws Throwable {
        // create a supply for an item on a location (given in the request body)
        if (supplyRepository.findById(createSupplyRequest.getSupplyId()).isPresent()) {
            // supply id is not unique
            throw new DuplicateResourceException("a supply with same supplyId already exists; please provide a unique supply id");
        } else {
            // the supplyId is unique
            // if the itemId and the locationId are present in the items and locations table
            if (locationRepository.findById(createSupplyRequest.getLocationId()).isPresent()
                    && itemRepository.findById(createSupplyRequest.getItemId()).isPresent()) {
                // create the supply
                Supply supply = new Supply(createSupplyRequest);
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
                throw new CreateResourceOperationNotAllowed("there are no items and locations for your requested create supply; " +
                        "please provide an item id and a location id that exists");
            }
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
