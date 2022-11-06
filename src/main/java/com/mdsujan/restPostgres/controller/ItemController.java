package com.mdsujan.restPostgres.controller;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.response.ItemResponse;
import com.mdsujan.restPostgres.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ItemController {
    //    @GetMapping("/items")
//    public String getItems() {
//        return "it works";
//    }
    @Autowired
    ItemService itemService;

    @GetMapping("/items")
    public List<ItemResponse> getItems() {
        List<Item> itemList = itemService.getAllItems();
        List<ItemResponse> itemResponseList = new ArrayList<>();

        // convert each Item obj to ItemResponse
        itemList.stream().forEach(
                item -> itemResponseList.add(new ItemResponse(item)));
        return itemResponseList;
    }


}
