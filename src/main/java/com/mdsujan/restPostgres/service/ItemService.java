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
        if (itemRepository.findById(createItemRequest.getId()).isPresent()) {
            return itemRepository.findById(createItemRequest.getId()).get();
        }
        // else we create a new item
        Item item = new Item(createItemRequest);
        itemRepository.save(item);
        return item;
    }

    public boolean deleteItemById(Long itemId) {
        try {
            itemRepository.deleteById(itemId);
            return true;
//            boolean isSupply = supplyRepository.findById(itemId).isPresent();
//            boolean isDemand = demandRepository.findById(itemId).isPresent();
//            if (isSupply || isDemand ) {
//                System.out.println("** CANNOT DELETE **");
//                return false;
//            }
//            // delete only if no supply or demand exists for this item
//            itemRepository.deleteById(itemId);
//            return true;
        } catch (Exception e) {
            System.out.println("******************* EXCEPTION"+e);
            return false;
        }
    }
}
