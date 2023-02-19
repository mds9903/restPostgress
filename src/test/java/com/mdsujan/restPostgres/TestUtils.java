package com.mdsujan.restPostgres;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.PaginatedResponse;
import com.mdsujan.restPostgres.response.SupplyResponse;

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

    public Location getTestLocation() {
        return Location.builder()
                .locationId(1L)
                .locationDesc("test description")
                .type("testType")
                .pickupAllowed(false)
                .deliveryAllowed(false)
                .shippingAllowed(false)
                .addrLine1("test line 1")
                .addrLine2("test line 2")
                .addrLine3("test line 3")
                .city("testCity")
                .state("testState")
                .country("testCountry")
                .pincode("123456")
                .build();
    }

    public Location getTestLocationPatchUpdate() {
        return Location.builder()
                .locationId(1L)
                .locationDesc("test description2")
                .shippingAllowed(true)
                .type("testType2")
                .addrLine1("test2 line 1")
                .state("testState")
                .pincode("987654")
                .build();
    }

    public Location getTestLocationPatchUpdated() {
        return Location.builder()
                .locationId(1L)
                .locationDesc("test description2")
                .type("testType2")
                .pickupAllowed(false)
                .deliveryAllowed(false)
                .shippingAllowed(true)
                .addrLine1("test2 line 1")
                .addrLine2("test line 2")
                .addrLine3("test line 3")
                .city("testCity")
                .state("testState2")
                .country("testCountry")
                .pincode("987654")
                .build();

    }

    public Supply getTestSupply() {
        return Supply.builder()
                .supplyId(1L)
                .supplyType(AllowedSupplyTypes.ONHAND)
                .supplyQty(1L)
                .itemId(1L)
                .locationId(1L)
                .build();
    }

    public SupplyResponse getTestSupplyResponse() {
        return SupplyResponse.builder()
                .supplyId(1L)
                .type(AllowedSupplyTypes.ONHAND)
                .supplyQty(1L)
                .itemId(1L)
                .locationId(1L)
                .build();
    }

    public UpdateSupplyRequest getTestUpdateSupplyRequestPatch() {
        return UpdateSupplyRequest.builder()
                .supplyQty(2L)
                .build();
    }

    public Supply getTestSupplyPatchUpdated() {
        return Supply.builder()
                .supplyId(1L)
                .supplyType(AllowedSupplyTypes.ONHAND)
                .supplyQty(2L)
                .itemId(1L)
                .locationId(1L)
                .build();
    }

    public SupplyResponse getTestSupplyResponsePatchUpdated() {
        return SupplyResponse.builder()
                .supplyId(1L)
                .type(AllowedSupplyTypes.ONHAND)
                .supplyQty(2L)
                .itemId(1L)
                .locationId(1L)
                .build();
    }

    public CreateSupplyRequest getCreateSupplyRequest() {
        return CreateSupplyRequest
                .builder()
                .supplyId(1L)
                .supplyType(AllowedSupplyTypes.ONHAND)
                .supplyQty(1L)
                .itemId(1L)
                .locationId(1L)
                .build();
    }

    public UpdateSupplyRequest getTestUpdateSupplyRequestPut() {
        return UpdateSupplyRequest.builder()
                .supplyType(AllowedSupplyTypes.ONHAND)
                .supplyQty(2L)
                .itemId(1L)
                .locationId(1L)
                .build();
    }
}
