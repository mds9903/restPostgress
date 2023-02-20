package com.mdsujan.restPostgres;

import com.mdsujan.restPostgres.entity.*;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.request.*;
import com.mdsujan.restPostgres.response.*;

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
                .category("test2") // update
                .itemType("test type2") // update
                .status("test status2") // update
                .pickupAllowed(false) // update
                .build();
    }

    public Item getTestItemUpdated() {
        return Item.builder()
                .itemId(123L)
                .itemDesc("test description")
                .category("test2") // updated
                .itemType("test type2") // updated
                .status("test status2") // updated
                .price(100.99)
                .pickupAllowed(false)
                .shippingAllowed(true)
                .deliveryAllowed(true)
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

    public Demand getTestDemand() {
        return Demand
                .builder()
                .demandId(1L)
                .demandType(AllowedDemandTypes.HARD_PROMISED)
                .demandQty(1L)
                .itemId(1L)
                .locationId(1L)
                .build();
    }

    public DemandResponse getTestDemandResponse() {
        return DemandResponse
                .builder()
                .demandId(1L)
                .demandType(AllowedDemandTypes.HARD_PROMISED)
                .demandQty(1L)
                .itemId(1L)
                .locationId(1L)
                .build();
    }

    public CreateDemandRequest getCreateDemandRequest() {
        return CreateDemandRequest
                .builder()
                .demandId(1L)
                .demandType(AllowedDemandTypes.HARD_PROMISED)
                .demandQty(1L)
                .itemId(1L)
                .locationId(1L)
                .build();
    }

    public UpdateDemandRequest getTestUpdateDemandRequestPut() {
        return UpdateDemandRequest
                .builder()
                .demandType(AllowedDemandTypes.HARD_PROMISED)
                .demandQty(2L) // update
                .itemId(1L)
                .locationId(1L)
                .build();
    }

    public UpdateDemandRequest getTestUpdateDemandRequestPatch() {
        return UpdateDemandRequest
                .builder()
                .demandQty(2L) // update
                .build();
    }

    public Demand getTestDemandUpdatedPatch() {
        return Demand
                .builder()
                .demandId(1L)
                .demandType(AllowedDemandTypes.HARD_PROMISED)
                .demandQty(2L) // updated
                .itemId(1L)
                .locationId(1L)
                .build();
    }

    public DemandResponse getTestDemandResponseUpdatedPatch() {
        return DemandResponse
                .builder()
                .demandId(1L)
                .demandType(AllowedDemandTypes.HARD_PROMISED)
                .demandQty(2L) // updated
                .itemId(1L)
                .locationId(1L)
                .build();
    }


    public Threshold getTestThreshold() {
        return Threshold
                .builder()
                .thresholdId(1L)
                .itemId(1L)
                .locationId(1L)
                .minThreshold(1L)
                .maxThreshold(10L)
                .build();
    }

    public ThresholdResponse getTestThresholdResponse() {
        return ThresholdResponse
                .builder()
                .thresholdId(1L)
                .itemId(1L)
                .locationId(1L)
                .minThreshold(1L)
                .maxThreshold(10L)
                .build();
    }


    public CreateThresholdRequest getTestCreateThresholdRequest() {
        return CreateThresholdRequest
                .builder()
                .thresholdId(1L)
                .itemId(1L)
                .locationId(1L)
                .minThreshold(1L)
                .maxThreshold(10L)
                .build();
    }


    public UpdateThresholdRequest getTestUpdateThresholdRequestPut() {
        return UpdateThresholdRequest
                .builder()
                .itemId(1L)
                .locationId(1L)
                .maxThreshold(10L)
                .minThreshold(5L) // update
                .build();
    }

    public UpdateThresholdRequest getTestUpdateThresholdRequestPatch() {
        return UpdateThresholdRequest
                .builder()
                .minThreshold(5L) // update
                .build();
    }

    public ThresholdResponse getTestUpdatedThresholdResponse() {
        return ThresholdResponse
                .builder()
                .thresholdId(1L)
                .itemId(1L)
                .locationId(1L)
                .minThreshold(5L) // updated
                .maxThreshold(10L)  // updated
                .build();
    }

    public Threshold getTestUpdatedThreshold() {
        return Threshold
                .builder()
                .thresholdId(1L)
                .itemId(1L)
                .locationId(1L)
                .minThreshold(5L) // updated
                .maxThreshold(10L)
                .build();
    }

    public AvailabilityV1Response getTestAvailabilityV1Response() {
        return AvailabilityV1Response
                .builder()
                .availableQty(1L)
                .itemId(1L)
                .locationId("1")
                .build();
    }

    public AvailabilityV2Response getTestAvailabilityV2Response() {
        return AvailabilityV2Response
                .builder()
                .availabilityQty(1L)
                .itemId(1L)
                .locationId(1L)
                .stockLevel("GREEN")
                .build();
    }

    public AvailabilityV3Response getTestAvailabilityV3Response() {
        return AvailabilityV3Response
                .builder()
                .availabilityQty(1L)
                .itemId(1L)
                .locationId(1L)
                .stockLevel("GREEN")
                .build();
    }
}
