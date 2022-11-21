package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.exceptionHandling.*;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateItemRequest;
import com.mdsujan.restPostgres.request.UpdateItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ItemService {
    // While constructing the ItemService object, if there's no constructor or setter method
    // to inject the ItemRepository bean, the container will use reflection
    // to inject ItemRepository into ItemService.
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    DemandRepository demandRepository;

    // Validator to validate programmatically
    private final Validator validator;

    ItemService(Validator validator) {
        this.validator = validator;
    }

    private void validateUpdateItemRequest(UpdateItemRequest updateItemRequest) throws Throwable {
        // validates the request body to have all the fields are there; for PUT methods
        Set<ConstraintViolation<UpdateItemRequest>> violationSet = validator.validate(updateItemRequest);
        if (!violationSet.isEmpty()) {
            throw new UpdateResourceRequestBodyInvalidException("some of the fields in the request body is missing; " +
                    "please send full request body for updating item");
        }
    }

    private void validateItemId(Long itemId) throws Throwable {
        // validates the itemId (passed in path-var)
        Set<ConstraintViolation<Long>> violationSet = validator.validate(itemId);
        if (!violationSet.isEmpty()) {
            throw new InvalidResourceIdException("itemId not valid; please provide a valid itemId of type Long");
        }
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long itemId) throws Throwable {
        validateItemId(itemId);
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("item not found for given itemId: '" + itemId + "'; please check itemId entered"));
    }

    public Item createItem(CreateItemRequest createItemRequest) throws Throwable {
        // new record should not be created if record already exists

        // if record with same id exists then simply return it
        if (itemRepository.findById(createItemRequest.getItemId()).isPresent()) {
            throw new DuplicateResourceException("an item with same itemId already exists; please provide a unique itemId in the request body");
//            return itemRepository.findById(createItemRequest.getItemId()).get();
        }
        // else we create a new item
        Item item = new Item(createItemRequest);
        itemRepository.save(item);
        return item;
    }

    public String deleteItemById(Long itemId) throws Throwable {
        validateItemId(itemId);

        // check if item exists
        if (itemRepository.findById(itemId).isPresent()) {
            // if any child records depend on this item
            if (supplyRepository.findByItemItemId(itemId).size() > 0 || demandRepository.findByItemItemId(itemId).size() > 0) {
                // this item cannot be deleted
//                return "Cannot delete item; it has child records";
                throw new ResourceConflictException("this item cannot be deleted; has child dependencies");
            }
            // else delete the existing item
            itemRepository.deleteById(itemId);
            return "Item with itemId=" + itemId + " successfully deleted.";
        }
        // else give proper message
//        return "Invalid itemId: no such item present";
        throw new ResourceNotFoundException("cannot delete; no such item found");
    }

    public Item updateItemByIdPut(Long itemId, UpdateItemRequest updateItemRequest) throws Throwable {
        // perform validations
        validateItemId(itemId);
        validateUpdateItemRequest(updateItemRequest);

        // "API must honor the itemId value passed in the input"
        if (!itemId.equals(updateItemRequest.getItemId())) {
            // itemId in the body is not matching the itemId in the path variable
            throw new UpdateResourceRequestBodyInvalidException("itemId in the body is not matching the itemId in the path variable; " +
                    "please provide the right itemId to avoid confusion");
        }
        if (itemRepository.findById(itemId).isPresent()) {

            Item itemToUpdate = itemRepository.findById(itemId).get();

            // update the itemToUpdate
            itemToUpdate.setItemDesc(updateItemRequest.getItemDesc());
            itemToUpdate.setCategory(updateItemRequest.getCategory());
            itemToUpdate.setType(updateItemRequest.getItemType());
            itemToUpdate.setStatus(updateItemRequest.getStatus());
            itemToUpdate.setPrice(updateItemRequest.getPrice());
            itemToUpdate.setPickupAllowed(updateItemRequest.getPickupAllowed());
            itemToUpdate.setShippingAllowed(updateItemRequest.getShippingAllowed());
            itemToUpdate.setDeliveryAllowed(updateItemRequest.getDeliveryAllowed());
            // save the new entity
            itemToUpdate = itemRepository.save(itemToUpdate);
            return itemToUpdate;
//            }
        } else {
            throw new ResourceNotFoundException("cannot update this item; item not found; please a correct itemId");
        }
    }


    public Item updateItemByIdPatch(Long itemId, UpdateItemRequest updateItemRequest) throws Throwable {

        validateItemId(itemId);

        // "API must honor the itemId value passed in the input"
        if (!itemId.equals(updateItemRequest.getItemId())) {
            // itemId in the body is not matching the itemId in the path variable
            throw new UpdateResourceRequestBodyInvalidException("itemId in the body is not matching the itemId in the path variable; " +
                    "please provide the right itemId to avoid confusion");
        }
        if (itemRepository.findById(itemId).isPresent()) {
            // find the record matching with the id
            Item itemToUpdate = itemRepository.findById(itemId).get();
            // update the itemToUpdate
            // since this is a patch request only present fields shall be updated
            if (updateItemRequest.getItemDesc() != null && !updateItemRequest.getItemDesc().isEmpty()) {
                itemToUpdate.setItemDesc(updateItemRequest.getItemDesc());
            }
            if (updateItemRequest.getCategory() != null && !updateItemRequest.getCategory().isEmpty()) {
                itemToUpdate.setCategory(updateItemRequest.getCategory());
            }
            if (updateItemRequest.getItemType() != null && !updateItemRequest.getItemType().isEmpty()) {
                itemToUpdate.setType(updateItemRequest.getItemType());
            }
            if (updateItemRequest.getStatus() != null && !updateItemRequest.getStatus().isEmpty()) {
                itemToUpdate.setStatus(updateItemRequest.getStatus());
            }
            if (updateItemRequest.getPrice() != null) {
                itemToUpdate.setPrice(updateItemRequest.getPrice());
            }
            if (updateItemRequest.getPickupAllowed() != null) {
                itemToUpdate.setPickupAllowed(updateItemRequest.getPickupAllowed());
            }
            if (updateItemRequest.getShippingAllowed() != null) {
                itemToUpdate.setShippingAllowed(updateItemRequest.getShippingAllowed());
            }
            if (updateItemRequest.getDeliveryAllowed() != null) {
                itemToUpdate.setDeliveryAllowed(updateItemRequest.getDeliveryAllowed());
            }
            // save the new entity
            itemToUpdate = itemRepository.save(itemToUpdate);
            return itemToUpdate;
        } else {
            throw new ResourceNotFoundException("cannot update item; item not found; please provide the correct itemId");
        }
    }
}
