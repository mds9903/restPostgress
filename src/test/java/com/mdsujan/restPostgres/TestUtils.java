package com.mdsujan.restPostgres;

import com.mdsujan.restPostgres.entity.Item;

public class TestUtils {
    // a class to provide variables and instances whenever needed during tests

    // get a list of items
    public Item getTestItem() {
        return Item.builder()
                .itemId(123L)
                .itemDesc("test description")
                .itemType("test type")
                .category("test")
                .deliveryAllowed(false)
                .shippingAllowed(false)
                .pickupAllowed(false)
                .build();
    }


}
