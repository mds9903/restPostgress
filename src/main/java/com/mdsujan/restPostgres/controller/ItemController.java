package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.request.CreateItemRequest;
import com.mdsujan.restPostgres.request.UpdateItemRequest;
import com.mdsujan.restPostgres.response.ItemResponse;
import com.mdsujan.restPostgres.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    @GetMapping("/") // return all items
    public List<ItemResponse> getAllItems() {

//        logger.error("Logger Error");
//        logger.warn("Logger Warn");
//        logger.info("Logger Info");
//        logger.debug("Logger Debug");
//        logger.trace("Logger Trace");


        List<Item> itemList = itemService.getAllItems();
        List<ItemResponse> itemResponseList = new ArrayList<>();

        // convert each Item obj to ItemResponse
        itemList.forEach(item -> itemResponseList.add(new ItemResponse(item)));
        logger.info("Response: " + itemResponseList); // log the response being sent
        return itemResponseList;


    }

    @GetMapping("/{itemId}") // return the details of specific itemId
    public ItemResponse getItem(@PathVariable Long itemId) {
        ItemResponse itemResponse = new ItemResponse(itemService.getItemById(itemId));
        logger.info("Response: " + itemResponse);

        return itemResponse;
    }

    @PostMapping("/") // create an item in the table
    public ItemResponse createItem(@RequestBody CreateItemRequest createItemRequest) {
        logger.info("InQueryRequest: "+createItemRequest);
        Item newItem = itemService.createItem(createItemRequest);
        ItemResponse itemResponse = new ItemResponse(newItem);
        logger.info("Response: "+itemResponse);
        return itemResponse;
    }

    @DeleteMapping("/{itemId}") // delete a specific item
    public String deleteItem(@PathVariable Long itemId) {

        String response = itemService.deleteItemById(itemId);
        logger.info("Response: "+response);
        return response;
    }

    @PutMapping("/{itemId}")
    public ItemResponse updateItemPut(@PathVariable Long itemId, @RequestBody UpdateItemRequest updateItemRequest) {
        logger.info("InQueryRequest: "+updateItemRequest);
        ItemResponse itemResponse = new ItemResponse(itemService.updateItemByIdPut(itemId, updateItemRequest));
        logger.info("Response: "+itemResponse);
        return itemResponse;
    }

    @PatchMapping("/{itemId}")
    public ItemResponse updateItemPatch(@PathVariable Long itemId, @RequestBody UpdateItemRequest updateItemRequest) {
        logger.info("InQueryRequest: "+updateItemRequest);
        ItemResponse itemResponse = new ItemResponse(itemService.updateItemByIdPatch(itemId, updateItemRequest));
        logger.info("Response: "+itemResponse);
        return itemResponse;
    }
}
