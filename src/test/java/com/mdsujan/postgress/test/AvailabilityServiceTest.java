package com.mdsujan.postgress.test;

import com.mdsujan.restPostgres.entity.*;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.*;
import com.mdsujan.restPostgres.response.AvailabilityV1Response;
import com.mdsujan.restPostgres.response.AvailabilityV2Response;
import com.mdsujan.restPostgres.response.AvailabilityV3Response;
import com.mdsujan.restPostgres.service.AvailabilityService;
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
@SpringBootTest(classes = AvailabilityService.class)
public class AvailabilityServiceTest {

    @Autowired
    private AvailabilityService availabilityService;

    @MockBean
    private SupplyRepository mockSupplyRepository;

    @MockBean
    private DemandRepository mockDemandRepository;

    @MockBean
    private ThresholdRepository mockThresholdRepository;

    private final Item mockItem1 = new Item(
            1L,
            "testDesc",
            "testCategory",
            "testType",
            "testStatus",
            19.99,
            false,
            false,
            false);
    private final Item mockItem2 = new Item(
            2L,
            "testDesc2",
            "testCategory2",
            "testType2",
            "testStatus2",
            29.99,
            true,
            true,
            true);
    private final Location mockLocation1 = new Location(
            1L,
            "testDesc",
            "testType",
            false,
            false,
            false,
            "testLine1",
            "testLine2",
            "testLine3",
            "testCity",
            "testState",
            "testCountry",
            "111111");
    private final Location mockLocation2 = new Location(
            2L,
            "testDesc2",
            "testType2",
            false,
            false,
            false,
            "testLine12",
            "testLine22",
            "testLine32",
            "testCity2",
            "testState2",
            "testCountry2",
            "222222");
    private final Supply mockSupply11 = new Supply(
            1L,
            AllowedSupplyTypes.ONHAND,
            1L,
            mockItem1,
            mockLocation1);
    private final Supply mockSupply12 = new Supply(
            2L,
            AllowedSupplyTypes.INTRANSIT,
            1L,
            mockItem1,
            mockLocation2);
    private final List<Supply> mockSupplyListItem1Location1 = List.of(mockSupply11);
    // supplies with itemId=2 and locationId=2
    private final List<Supply> getMockSupplyListItem1LocationAll = List.of(mockSupply11, mockSupply12);

    private final Demand mockDemand11 = new Demand(
            1L,
            AllowedDemandTypes.HARD_PROMISED,
            1L,
            mockItem1,
            mockLocation1);
    private final Demand mockDemand12 = new Demand(
            2L,
            AllowedDemandTypes.PLANNED,
            1L,
            mockItem1,
            mockLocation2);
    private final List<Demand> mockDemandListItem1Location1 = List.of(mockDemand11);
    // demands with itemId=2 and locationId=2
    private final List<Demand> getMockDemandListItem1LocationAll = List.of(mockDemand11, mockDemand12);

    private final Threshold mockThreshold11 = new Threshold(
            1L,
            mockItem1,
            mockLocation1,
            1L,
            1L);


    private final AvailabilityV1Response expectedQtyByItemAndLocationV1 = new AvailabilityV1Response(
            1L,
            1L,
            2L);

    private final AvailabilityV1Response expectedAvlQtyByItemV1 = new AvailabilityV1Response(
            1L,
            "NETWORK",
            2L);


    final AvailabilityV2Response expectedStockLevelV2 = new AvailabilityV2Response(
            1L,
            1L,
            2L,
            "Green");

    final AvailabilityV3Response expectedStockLevelV3 = new AvailabilityV3Response(
            1L,
            1L,
            2L,
            "Green");

    @Test
    public void testGetAvlQtyByItemAndLocationV1() throws Throwable {
        // stub
        Mockito
                .when(mockSupplyRepository.findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockSupplyListItem1Location1);
        Mockito
                .when(mockDemandRepository.findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockDemandListItem1Location1);

        // when
        AvailabilityV1Response availabilityV1Response = availabilityService.getAvlQtyByItemAndLocationV1(1L, 1L);

        // test
        assertThat(availabilityV1Response.getAvailableQty()).isEqualTo(expectedQtyByItemAndLocationV1.getAvailableQty());
    }

    //
    @Test
    public void testGetAvlQtyByItemV1() throws Throwable {
        // stub
        Mockito
                .when(mockSupplyRepository.findByItemItemId(1L))
                .thenReturn(getMockSupplyListItem1LocationAll);
        Mockito
                .when(mockDemandRepository.findByItemItemId(1L))
                .thenReturn(getMockDemandListItem1LocationAll);

        // when
        AvailabilityV1Response actualResponse = availabilityService.getAvlQtyByItemV1(1l);

        // test
        assertThat(actualResponse.getAvailableQty()).isEqualTo(expectedAvlQtyByItemV1.getAvailableQty());
    }

    @Test
    public void testGetStockLevelV2() throws Throwable {
        // stubs
        Mockito
                .when(mockDemandRepository
                        .findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockDemandListItem1Location1);

        Mockito
                .when(mockSupplyRepository
                        .findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockSupplyListItem1Location1);

        Mockito
                .when(mockThresholdRepository
                        .findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockThreshold11);

        // when
        AvailabilityV2Response actualResponse = availabilityService.getStockLevelV2(
                1L, 1L);

        // assert
        assertThat(actualResponse.getStockLevel()).isEqualTo(expectedStockLevelV2.getStockLevel());
    }

    @Test
    public void testGetStockLevelV3() throws Throwable {

        // stubs
        Mockito
                .when(mockDemandRepository
                        .findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockDemandListItem1Location1);

        Mockito
                .when(mockSupplyRepository
                        .findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockSupplyListItem1Location1);

        Mockito
                .when(mockThresholdRepository
                        .findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockThreshold11);


        // when
        AvailabilityV3Response actualResponse = availabilityService.getStockLevelV3(
                1L, 1L);

        // assert
        assertThat(actualResponse.getStockLevel()).isEqualTo(expectedStockLevelV3.getStockLevel());
    }

}
