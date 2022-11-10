package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateItemRequest;
import com.mdsujan.restPostgres.request.UpdateItemRequest;
import com.mdsujan.restPostgres.response.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public Item createItem(CreateItemRequest createItemRequest) {
        // new record should not be created if record already exists

        // if record with same id exists then simply return it
        if (itemRepository.findById(createItemRequest.getItemId()).isPresent()) {
            return itemRepository.findById(createItemRequest.getItemId()).get();
        }
        // else we create a new item
        Item item = new Item(createItemRequest);
        itemRepository.save(item);
        return item;
    }

    public String deleteItemById(Long itemId) {
        try {
            // check if item exists
            if(itemRepository.findById(itemId).isPresent()){
                // if any child records depend on this item
                if(supplyRepository.findByItemItemId(itemId).size() > 0 || demandRepository.findByItemItemId(itemId).size() > 0){
                    // this item cannot be deleted
                    return "Cannot delete item; it has child records";
                }
                // delete the existing item
                itemRepository.deleteById(itemId);
                return "Item with itemId="+itemId+" successfully deleted.";
            }
            // else give proper message
            return "Invalid itemId: no such item present";
        } catch (Exception e) {
            return "EXCEPTION OCCURRED" + e;
        }
    }

    public Item updateItemById(Long itemId, UpdateItemRequest updateItemRequest) {
        // find the record matching with the id
        Item itemToUpdate = itemRepository.findById(itemId).get();
        // "API must honor the itemId value passed in the input"
        // if the new item does not have id then we abort;
        if (!Objects.equals(updateItemRequest.getId(), itemId)) {
            // cannot change the id of item
//            return null;
            return itemToUpdate;
        }
        try{

            // update the itemToUpdate
            if (updateItemRequest.getDesc() != null || !updateItemRequest.getDesc().isEmpty()) {
                itemToUpdate.setItemDesc(updateItemRequest.getDesc());
            }
            if (updateItemRequest.getCategory() != null || !updateItemRequest.getCategory().isEmpty()) {
                itemToUpdate.setCategory(updateItemRequest.getCategory());
            }
            if (updateItemRequest.getType() != null || !updateItemRequest.getType().isEmpty()) {
                itemToUpdate.setType(updateItemRequest.getType());
            }
            if (updateItemRequest.getStatus() != null || !updateItemRequest.getStatus().isEmpty()) {
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
        }catch (Exception e){
            System.out.println("Exception occurred: "+e.getMessage());
        }
        // anyhow we send itemToUpdate
        // in case there is any issue with the UpdateRequestItem not having consistency with the oldItem
        // then itemToUpdate will have old item's details
        // otherwise it will have updated details
        return itemToUpdate;
    }
}
