package com.mdsujan.restPostgres.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.exceptionHandling.DuplicateResourceException;
import com.mdsujan.restPostgres.exceptionHandling.ResourceConflictException;
import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.exceptionHandling.UpdateResourceRequestBodyInvalidException;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.response.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    DemandRepository demandRepository;

    @Autowired
    ThresholdRepository thresholdRepository;


    //    @Cacheable("items")
    public List<Item> getAllItems() {

        System.out.println("getting all items..");
        return itemRepository.findAll();
    }


    public Item getItemById(Long itemId) throws Throwable {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "item not found for given itemId: '"
                                + itemId + "'; please check itemId entered"));
    }

    //    @CacheEvict(value = "items", allEntries = true)
    public Item createItem(Item createItemRequest) throws Throwable {
        // new record should not be created if record already exists

        // if record with same id exists then simply return it
        if (itemRepository.findById(createItemRequest.getItemId()).isPresent()) {
            throw new DuplicateResourceException("an item with same itemId already exists; please provide a unique " +
                    "itemId in the request body");
        }
        // else we create a new item
        Item response = itemRepository.save(createItemRequest);
        System.out.println("itemId of created item: " + response.getItemId());
        return response;
    }

    public Item createItemNoValidate(Item createItemRequest) throws Throwable {

        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonString = mapper.writeValueAsString(createItemRequest);
        return itemRepository.save(createItemRequest);


//        // new record should not be created if record already exists
//
//        // if record with same id exists then simply return it
//        if (itemRepository.findById(createItemRequest.getItemId()).isPresent()) {
//            throw new DuplicateResourceException("an item with same itemId already exists; please provide a unique
//            itemId in the request body");
//        }
//        // else we create a new item
//        return itemRepository.save(createItemRequest);
    }

    public List<Item> createItems(List<Item> itemList) throws Throwable {
        // for each item in itemList
        // perform item creation
        // also handle validations and errors elegantly
        List<Item> itemsCreated = new ArrayList<>();
//        List<Item> itemsNotCreated = new ArrayList<>();

        for (Item item : itemList) {
            try {
                itemsCreated.add(createItem(item));
            } catch (DuplicateResourceException e) {
                throw new DuplicateResourceException("For item #" + (itemList.indexOf(item) + 1) + "; an item with " +
                        "same itemId already exists; please provide a unique " +
                        "itemId in the request body");
            }
        }

        return itemsCreated;
    }

    public String deleteItemById(Long itemId) throws Throwable {
        // check if item exists
        if (itemRepository.findById(itemId).isPresent()) {
            // if any child records depend on this item
            if (supplyRepository.findByItemId(itemId).size() > 0
                    || demandRepository.findByItemId(itemId).size() > 0
                    || thresholdRepository.findByItemId(itemId).size() > 0) {
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

    public Item updateItemByIdPut(Long itemId, Item updateItemRequest) throws Throwable {
        // "API must honor the itemId value passed in the input"
        if (!itemId.equals(updateItemRequest.getItemId())) {
            // itemId in the body is not matching the itemId in the path variable
            throw new UpdateResourceRequestBodyInvalidException("itemId in the body is not matching the itemId in the" +
                    " path variable; " + "please provide the " +
                    "right itemId to avoid confusion");
        }
        if (itemRepository.findById(itemId).isPresent()) {
            Item itemToUpdate = itemRepository.findById(itemId).get();
            // update the itemToUpdate
            itemToUpdate.setItemDesc(updateItemRequest.getItemDesc());
            itemToUpdate.setCategory(updateItemRequest.getCategory());
            itemToUpdate.setItemType(updateItemRequest.getItemType());
            itemToUpdate.setStatus(updateItemRequest.getStatus());
            itemToUpdate.setPrice(updateItemRequest.getPrice());
            itemToUpdate.setPickupAllowed(updateItemRequest.getPickupAllowed());
            itemToUpdate.setShippingAllowed(updateItemRequest.getShippingAllowed());
            itemToUpdate.setDeliveryAllowed(updateItemRequest.getDeliveryAllowed());
            // save the new entity
            itemToUpdate = itemRepository.save(itemToUpdate);
            return updateItemRequest;
//            }
        } else {
            throw new ResourceNotFoundException("cannot update this item; item not found; please enter a correct " +
                    "itemId");
        }
    }

    public Item updateItemByIdPatch(Long itemId, Item updateItemRequest) throws Throwable {
        // "API must honor the itemId value passed in the input"
        if (!itemId.equals(updateItemRequest.getItemId())) {
            // itemId in the body is not matching the itemId in the path variable
            throw new UpdateResourceRequestBodyInvalidException("itemId in the body is not matching the itemId in the" +
                    " path variable; " + "please provide the " +
                    "right itemId to avoid confusion");
        }
        if (itemRepository.findById(itemId).isPresent()) {
            // find the record matching with the id
            Item itemToUpdate = itemRepository.findById(itemId).get();
            // update the itemToUpdate

            if (updateItemRequest.getItemDesc() != null && !updateItemRequest.getItemDesc().isEmpty()) {
                itemToUpdate.setItemDesc(updateItemRequest.getItemDesc());
            }

            if (updateItemRequest.getCategory() != null && !updateItemRequest.getCategory().isEmpty()) {
                itemToUpdate.setCategory(updateItemRequest.getCategory());
            }

            if (updateItemRequest.getItemType() != null && !updateItemRequest.getItemType().isEmpty()) {
                itemToUpdate.setItemType(updateItemRequest.getItemType());
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
            return itemRepository.save(itemToUpdate);
        } else {
            throw new ResourceNotFoundException("cannot update item; item not found; please provide the correct " +
                    "itemId");
        }
    }

    public PaginatedResponse getAllItemsPaginated(Integer pageSize, Integer pageNum) {
        List<Item> items = itemRepository.findAll(PageRequest.of(pageNum - 1, pageSize)).getContent();
        Long maxPages = itemRepository.count() / pageSize;

        return new PaginatedResponse(items, maxPages, pageNum, pageSize);
    }

}
