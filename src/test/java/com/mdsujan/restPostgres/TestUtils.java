package com.mdsujan.restPostgres;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.response.PaginatedResponse;

import java.util.List;

public class TestUtils {
    // a class to provide variables and instances whenever needed during tests

    // get a list of items
    public Item getTestItem() {
        return Item.builder()
                .itemId(123L)
                .itemDesc("test description")
                .category("test")
                .itemType("test type")
                .status("test status")
                .price(100.99)
                .pickupAllowed(false)
                .shippingAllowed(false)
                .deliveryAllowed(false)
                .build();
    }

    public Item getTestItemPatchUpdate() {
        return Item.builder()
                .itemId(123L)
                .category("test2")
                .itemType("test type2")
                .status("test status2")
                .pickupAllowed(false)
                .build();
    }

    public Item getTestItemPatchUpdated() {
        return Item.builder()
                .itemId(123L)
                .itemDesc("test description")
                .category("test2")
                .itemType("test type2")
                .status("test status2")
                .price(100.99)
                .pickupAllowed(false)
                .shippingAllowed(false)
                .deliveryAllowed(false)
                .build();
    }

    public PaginatedResponse getTestPaginatedResponse() {
        return PaginatedResponse.builder()
                .items(List.of(getTestItem()))
                .pageNum(1)
                .pageSize(1)
                .maxPages(1)
                .build();
    }

}
