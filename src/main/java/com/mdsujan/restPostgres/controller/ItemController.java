package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.app.ExceptionHandler;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.excpetions.DuplicateEntryException;
import com.mdsujan.restPostgres.request.UpdateItemRequest;
import com.mdsujan.restPostgres.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController extends ExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    @GetMapping("/") // return all items
    public List<Item> getAllItems() {

        List<Item> itemList = itemService.getAllItems();
//        List<Item> itemResponseList = new ArrayList<>();

        // convert each Item obj to Item
//        itemList.forEach(item -> itemResponseList.add(new Item(item)));
        logger.info("Response: " + itemList); // log the response being sent
        return itemList;


    }

    @GetMapping("/{itemId}") // return the details of specific itemId
    public Item getItem(@PathVariable Long itemId) {
        Item item = itemService.getItemById(itemId);
        logger.info("Response: " + item);
        return item;
    }

    @PostMapping("/") // create an item in the table
    public Item createItem(@RequestBody Item item) throws DuplicateEntryException {
        logger.info("InQueryRequest: " + item);
        Item itemResponse = itemService.createItem(item);
        logger.info("Response: " + itemResponse);
        return itemResponse;
    }

    @DeleteMapping("/{itemId}") // delete a specific item
    public String deleteItem(@PathVariable Long itemId) {

        String response = itemService.deleteItemById(itemId);
        logger.info("Response: " + response);
        return response;
    }

    @PutMapping("/{itemId}")
    public Item updateItemPut(@PathVariable Long itemId, @RequestBody UpdateItemRequest updateItemRequest) {
        logger.info("InQueryRequest: " + updateItemRequest);
        Item itemResponse = itemService.updateItemByIdPut(itemId, updateItemRequest);
        logger.info("Response: " + itemResponse);
        return itemResponse;
    }

    @PatchMapping("/{itemId}")
    public Item updateItemPatch(@PathVariable Long itemId, @RequestBody UpdateItemRequest updateItemRequest) {
        logger.info("InQueryRequest: " + updateItemRequest);
        Item itemResponse = itemService.updateItemByIdPatch(itemId, updateItemRequest);
        logger.info("Response: " + itemResponse);
        return itemResponse;
    }
}
