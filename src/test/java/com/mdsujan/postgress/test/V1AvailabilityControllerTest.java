package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.controller.AvailabilityV1Controller;
import com.mdsujan.restPostgres.controller.SupplyController;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.response.AvailabilityV1Response;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.service.SupplyService;
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
@SpringBootTest(classes = AvailabilityV1Controller.class)
public class V1AvailabilityControllerTest {

    @Autowired
    private AvailabilityV1Controller availabilityV1Controller;
    @MockBean
    private SupplyRepository mockSupplyRepository;

    @MockBean
    private DemandRepository mockDemandRepository;

    private final Item mockItem1 = new Item(1L, "testDesc", "testCategory", "testType", "testStatus", 99.99, false, false, false);
    private final Item mockItem2 = new Item(2L, "testDesc", "testCategory", "testType", "testStatus", 99.99, false, false, false);
    private final Location mockLocation1 = new Location(1L, "testLocationDesc", "testType", true, true, true, "testCity", "testState", "testCountry", "testPincode");
    private final Location mockLocation2 = new Location(2L, "testLocationDesc", "testType", true, true, true, "testCity", "testState", "testCountry", "testPincode");
    private final Demand mockDemand11 = new Demand(1L, AllowedDemandTypes.HARD_PROMISED, 1L, mockItem1, mockLocation1);
    private final Demand mockDemand12 = new Demand(2L, AllowedDemandTypes.PLANNED, 1L, mockItem1, mockLocation2);
    private final List<Demand> mockDemandList11 = List.of(mockDemand11);
    private final List<Demand> mockDemandList12 = List.of(mockDemand12);
    private final Supply mockSupply11 = new Supply(1L, AllowedSupplyTypes.ONHAND, 1L, mockItem1, mockLocation1);
    private final Supply mockSupply12 = new Supply(2L, AllowedSupplyTypes.INTRANSIT, 1L, mockItem1, mockLocation2);
    private final List<Supply> mockSupplyList11 = List.of(mockSupply11);
    private final List<Supply> mockSupplyList12 = List.of(mockSupply12);
    private  final AvailabilityV1Response expectedAvailabilityQtyResponse11 = new AvailabilityV1Response(
            1L,
            1L,
            2L);

    private  final AvailabilityV1Response expectedAvailabilityQtyResponse1AllLoc = new AvailabilityV1Response(
            1L,
            1L,
            2L);


    @Test
    public void testGetAvailabilityQtyByItemAndLocation() throws Throwable {
        // stubs
        Mockito
                .when(mockDemandRepository.findByItemItemIdAndLocationLocationId(
                        1L, 1L))
                .thenReturn(mockDemandList11);

        Mockito
                .when(mockSupplyRepository.findByItemItemIdAndLocationLocationId(
                        1L, 1L))
                .thenReturn(mockSupplyList11);

        // when
        AvailabilityV1Response actualResponse = availabilityV1Controller.getAvailabilityQtyByItemAndLocation(
                1L, 1L);

        // assert
        assertThat(actualResponse.getAvailableQty()).isEqualTo(expectedAvailabilityQtyResponse11.getAvailableQty());
    }

    @Test
    public void testGetAllLocationAvailabilityQty() throws Throwable {
        // stubs
        Mockito
                .when(mockDemandRepository.findByItemItemId(1L))
                .thenReturn(List.of(mockDemand11, mockDemand12));

        Mockito
                .when(mockSupplyRepository.findByItemItemId(1L))
                .thenReturn(List.of(mockSupply11, mockSupply12));

        // when
        AvailabilityV1Response actualResponse = availabilityV1Controller.getAllLocationAvailabilityQty(1L);

        // assert
        assertThat(actualResponse.getAvailableQty()).isEqualTo(expectedAvailabilityQtyResponse1AllLoc.getAvailableQty());
    }
}