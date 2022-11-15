package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.excpetions.DuplicateEntryException;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateItemRequest;
import com.mdsujan.restPostgres.request.UpdateItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


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

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }


    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).get();
    }

    public Item createItem(CreateItemRequest createItemRequest) throws DuplicateEntryException {
        // new record should not be created if record already exists

        // if record with same id exists then simply return it
        if (itemRepository.findById(createItemRequest.getItemId()).isPresent()) {
            // since the same item already exists we throw an excpetion
            // named "DuplicateEntryException" with a message "Item with given itemId already exists"

            throw new DuplicateEntryException(itemRepository.findById(createItemRequest.getItemId()).get());

//            return itemRepository.findById(createItemRequest.getItemId()).get();
        }
        // else we create a new item
        Item item = new Item(createItemRequest);
        itemRepository.save(item);
        return item;
    }

    public String deleteItemById(Long itemId) {
        try {
            // check if item exists
            if (itemRepository.findById(itemId).isPresent()) {
                // if any child records depend on this item
                if (supplyRepository.findByItemItemId(itemId).size() > 0 || demandRepository.findByItemItemId(itemId).size() > 0) {
                    // this item cannot be deleted
                    return "Cannot delete item; it has child records";
                }
                // delete the existing item
                itemRepository.deleteById(itemId);
                return "Item with itemId=" + itemId + " successfully deleted.";
            }
            // else give proper message
            return "Invalid itemId: no such item present";
        } catch (Exception e) {
            return "EXCEPTION OCCURRED" + e;
        }
    }

    public Item updateItemByIdPut(Long itemId, UpdateItemRequest updateItemRequest) {

        // "API must honor the itemId value passed in the input"

        // find the record matching with the id
        Item itemToUpdate = itemRepository.findById(itemId).get();
        try {

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
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return itemToUpdate;
    }

    public Item updateItemByIdPatch(Long itemId, UpdateItemRequest updateItemRequest) {
        // find the record matching with the id
        Item itemToUpdate = itemRepository.findById(itemId).get();

        try {
            // test
            String statusTest = updateItemRequest.getStatus();
            // update the itemToUpdate

            // since this is a path request only present fields shall be updated
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
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e);
        }
        return itemToUpdate;
    }
}
