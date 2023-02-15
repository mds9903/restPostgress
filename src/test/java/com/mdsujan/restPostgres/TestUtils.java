package com.mdsujan.restPostgres;

import com.mdsujan.restPostgres.entity.Item;

import java.util.List;

public class TestUtils {
    // a class to provide variables and instances whenever needed during tests

    // get a list of items
    public List<Item> getItemsList() {
        Item item1 = Item.builder()
                .itemId(123L)
                .itemDesc("test description")
                .itemType("test type")
                .category("test")
                .deliveryAllowed(false)
                .shippingAllowed(false)
                .pickupAllowed(false)
                .build();
        return List.of(item1);
    }

}
