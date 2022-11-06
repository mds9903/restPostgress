package com.mdsujan.restProges.controller;

import com.mdsujan.restProges.response.ItemResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ItemController {
//    @GetMapping("/items")
//    public String getItems() {
//        return "it works";
//    }

    @GetMapping("/items")
    public ItemResponse getItems() {
        ItemResponse itemResponse = new ItemResponse(
                "item1",
                "sample item",
                "test",
                "sample",
                "testStatus",
                1000.0,
                true,
                true,
                true);
        return itemResponse;
    }
}
