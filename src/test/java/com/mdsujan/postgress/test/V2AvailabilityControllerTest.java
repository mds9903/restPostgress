package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.controller.AvailabilityV2Controller;
import com.mdsujan.restPostgres.controller.SupplyController;
import com.mdsujan.restPostgres.entity.*;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.response.AvailabilityV2Response;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.service.SupplyService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvailabilityV2Controller.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class V2AvailabilityControllerTest {

    @Autowired
    AvailabilityV2Controller availabilityV2Controller;
    @MockBean
    SupplyRepository mockSupplyRepository;

    @MockBean
    ThresholdRepository mockThresholdRepository;

    @MockBean
    DemandRepository mockDemandRepository;

    final Item mockItem1 = new Item(1L, "testDesc", "testCategory", "testType", "testStatus", 99.99, false, false, false);
    final Item mockItem2 = new Item(2L, "testDesc", "testCategory", "testType", "testStatus", 99.99, false, false, false);
    final Location mockLocation1 = new Location(1L, "testLocationDesc", "testType", true, true, true, "testCity", "testState", "testCountry", "testPincode");
    final Location mockLocation2 = new Location(2L, "testLocationDesc", "testType", true, true, true, "testCity", "testState", "testCountry", "testPincode");
    final Demand mockDemand11 = new Demand(1L, AllowedDemandTypes.HARD_PROMISED, 1L, mockItem1, mockLocation1);
    final Demand mockDemand12 = new Demand(2L, AllowedDemandTypes.PLANNED, 1L, mockItem1, mockLocation2);
    final List<Demand> mockDemandList11 = List.of(mockDemand11);
    final List<Demand> mockDemandList12 = List.of(mockDemand12);
    final Supply mockSupply11 = new Supply(1L, AllowedSupplyTypes.ONHAND, 1L, mockItem1, mockLocation1);
    final Supply mockSupply12 = new Supply(2L, AllowedSupplyTypes.INTRANSIT, 1L, mockItem1, mockLocation2);
    final List<Supply> mockSupplyList11 = List.of(mockSupply11);
    final List<Supply> mockSupplyList12 = List.of(mockSupply12);
    final Threshold mockThreshold11 =  new Threshold(
            1L,
            mockItem1,
            mockLocation1,
            1L,
            1L);
    final AvailabilityV2Response expectedAvailabilityQtyResponse11 = new AvailabilityV2Response(
            1L,
            1L,
            2L,
            "Green");



    @Test
    public void testgetAvailabilityStockLevel() throws Throwable {
        // stubs
        Mockito
                .when(mockDemandRepository
                        .findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockDemandList11);

        Mockito
                .when(mockSupplyRepository
                        .findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockSupplyList11);

        Mockito
                .when(mockThresholdRepository
                        .findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockThreshold11);

        // when
        AvailabilityV2Response actualResponse = availabilityV2Controller.getAvailabilityStockLevel(
                1L, 1L);

        // assert
        assertThat(actualResponse.getStockLevel()).isEqualTo(expectedAvailabilityQtyResponse11.getStockLevel());
    }

}