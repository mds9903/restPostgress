package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    // While constructing the ItemService object, if there's no constructor or setter method
    // to inject the ItemRepository bean,
    // the container will use reflection to inject ItemRepository into ItemService.
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    DemandRepository demandRepository;
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }


    public Item getStudentById(Long itemId) {
        return itemRepository.findById(itemId).get();
    }

    public Item createItem(CreateItemRequest createItemRequest) {
        Item newItem = new Item(createItemRequest);
        // new record should not be created if record already exists
        // if record does not exist then we persist into the table item
        if(itemRepository.findById(createItemRequest.getId()).get() == null){
            itemRepository.save(newItem);
            return newItem;
        }
        // else return the old record
        return itemRepository.findById(createItemRequest.getId()).get();
    }

    public boolean deleteItemById(Long itemId) {
        try {
            // delete only if no supply or demand exists for this item

            itemRepository.deleteById(itemId);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
