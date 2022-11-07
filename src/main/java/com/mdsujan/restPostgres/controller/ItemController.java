package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.request.CreateItemRequest;
import com.mdsujan.restPostgres.response.ItemResponse;
import com.mdsujan.restPostgres.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping("/") // return all items from table item
    public List<ItemResponse> getItems() {
        List<Item> itemList = itemService.getAllItems();
        List<ItemResponse> itemResponseList = new ArrayList<>();

        // convert each Item obj to ItemResponse
        itemList.stream().forEach(
                item -> itemResponseList.add(new ItemResponse(item)));
        return itemResponseList;
    }

    @GetMapping("/{itemId}") // return the details of specific itemId
    public ItemResponse getItemWithId(@PathVariable Long itemId){
        return new ItemResponse(itemService.getStudentById(itemId));
    }

    @PostMapping("/") // create an item in the table
    public ItemResponse createItem(@Valid @RequestBody CreateItemRequest createItemRequest){
        Item newItem = itemService.createItem(createItemRequest);
        return new ItemResponse(newItem);
    }

    @DeleteMapping("/{itemId}") // delete a specific item
    public String deleteItem(@PathVariable Long itemId){
        if(itemService.deleteItemById(itemId)){
            return "Item deleted successfully";
        }
        return "Item not deleted";
    }

}
