package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.exceptionHandling.DuplicateItemException;
import com.mdsujan.restPostgres.exceptionHandling.ItemConflictException;
import com.mdsujan.restPostgres.exceptionHandling.ItemIdInvalidException;
import com.mdsujan.restPostgres.request.CreateItemRequest;
import com.mdsujan.restPostgres.request.UpdateItemRequest;
import com.mdsujan.restPostgres.response.ItemResponse;
import com.mdsujan.restPostgres.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    @GetMapping("/") // retrieve all all items
    public List<ItemResponse> getAllItems() {
        List<Item> itemList = itemService.getAllItems();
        List<ItemResponse> itemResponseList = new ArrayList<>();

        // convert each Item obj to ItemResponse
        itemList.forEach(item -> itemResponseList.add(new ItemResponse(item)));
        logger.info("Response: " + itemResponseList); // log the response being sent
        return itemResponseList;
    }

    @GetMapping(value = "/{itemId}") // return the details of specific itemId
    public ItemResponse getItem(@PathVariable Long itemId) throws Throwable {

        ItemResponse itemResponse = new ItemResponse(itemService.getItemById(itemId));
        logger.info("Response: " + itemResponse);
        return itemResponse;

        // check GlobalExceptionHandler.java for more info
//        try {
//            ItemResponse itemResponse = new ItemResponse(itemService.getItemById(itemId));
//            logger.info("Response: " + itemResponse);
//            return itemResponse;
//        }catch (MethodArgumentTypeMismatchException e){
//            throw new ItemIdInvalidException("itemId should not be a string; itemId is a number");
//        }
    }

    @PostMapping("/") // create an item in the table
    public ItemResponse createItem(@RequestBody CreateItemRequest createItemRequest) throws Throwable {
        logger.info("InQueryRequest: " + createItemRequest);
        Item newItem = itemService.createItem(createItemRequest);
        ItemResponse itemResponse = new ItemResponse(newItem);
        logger.info("Response: " + itemResponse);
        return itemResponse;
    }

    @DeleteMapping("/{itemId}") // delete a specific item
    public String deleteItem(@PathVariable Long itemId) throws Throwable {
        String response = itemService.deleteItemById(itemId);
        logger.info("Response: " + response);
        return response;
    }

    @PutMapping("/{itemId}")
    public ItemResponse updateItemPut(@PathVariable Long itemId, @RequestBody UpdateItemRequest updateItemRequest) throws Throwable {
        logger.info("InQueryRequest: " + updateItemRequest);
        ItemResponse itemResponse = new ItemResponse(itemService.updateItemByIdPut(itemId, updateItemRequest));
        logger.info("Response: " + itemResponse);
        return itemResponse;
    }

    @PatchMapping("/{itemId}")
    public ItemResponse updateItemPatch(@PathVariable Long itemId, @RequestBody UpdateItemRequest updateItemRequest) throws Throwable {
        logger.info("InQueryRequest: " + updateItemRequest);
        ItemResponse itemResponse = new ItemResponse(itemService.updateItemByIdPatch(itemId, updateItemRequest));
        logger.info("Response: " + itemResponse);
        return itemResponse;
    }
}
