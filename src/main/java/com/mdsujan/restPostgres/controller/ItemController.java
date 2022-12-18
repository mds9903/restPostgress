package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController // controller and response body combo
@RequestMapping("/items") // the uri this controller should be mapped to
public class ItemController {

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    @GetMapping(value = "/") // retrieve all all items
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping(value = "/paginated") // retrieve all items with pagination
    public List<Item> getAllItemsPaginated(@RequestParam int pageSize, @RequestParam int pageNum) {
        return itemService.getAllItemsPaginated(pageSize, pageNum);
    }


    @GetMapping(value = "/{itemId}") // return the details of specific itemId
    public Item getItem(@PathVariable @Valid Long itemId) throws Throwable {
        Item itemResponse = itemService.getItemById(itemId);
        logger.info("Response: " + itemResponse);
        return itemResponse;
    }

    @DeleteMapping("/{itemId}") // delete a specific item
    public String deleteItem(@PathVariable @Valid Long itemId) throws Throwable {
        String response = itemService.deleteItemById(itemId);
        logger.info("Response: " + response);
        return response;
    }

    @PostMapping("/") // create an item in the table
    public Item createItem(@RequestBody @Valid Item createItemRequest) throws Throwable {
        logger.info("InQueryRequest: " + createItemRequest);
        Item itemResponse = itemService.createItem(createItemRequest);
        logger.info("Response: " + itemResponse);
        return itemResponse;
    }

    @PutMapping("/{itemId}")
    public Item updateItemPut(@PathVariable @Valid Long itemId, @RequestBody @Valid Item updateItemRequest) throws Throwable {
        logger.info("InQueryRequest: " + updateItemRequest);
        Item itemResponse = itemService.updateItemByIdPut(itemId, updateItemRequest);
        logger.info("Response: " + itemResponse);
        return itemResponse;
    }

    @PatchMapping("/{itemId}")
    public Item updateItemPatch(@PathVariable @Valid Long itemId, @RequestBody Item updateItemRequest) throws Throwable {
        logger.info("InQueryRequest: " + updateItemRequest);
        Item itemResponse = itemService.updateItemByIdPatch(itemId, updateItemRequest);
        logger.info("Response: " + itemResponse);
        return itemResponse;
    }
}
