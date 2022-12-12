package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.controller.AvailabilityController;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
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
@SpringBootTest(classes = AvailabilityController.class)
public class AvailabilityControllerTest {

    @Autowired
    private AvailabilityController availabilityController;

    @MockBean
    private AvailabilityService mockAvailabilityService;

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
        // stubs
        Mockito
                .when(mockAvailabilityService.getAvlQtyByItemAndLocationV1(
                        1L, 1L))
                .thenReturn(expectedQtyByItemAndLocationV1);

        AvailabilityV1Response actualResponse = availabilityController.getAvlQtyByItemAndLocationV1(
                1L, 1L);

        // assert
        assertThat(actualResponse)
                .isEqualTo(expectedQtyByItemAndLocationV1);
    }

    @Test
    public void testGetAllLocationsAvailabilityQtyV1() throws Throwable {
        // stubs
        Mockito
                .when(mockAvailabilityService.getAvlQtyByItemV1(1L))
                .thenReturn(expectedAvlQtyByItemV1);

        // when
        AvailabilityV1Response actualResponse = availabilityController.getAllLocationAvailabilityQtyV1(1L);

        // assert
        assertThat(actualResponse).isEqualTo(expectedAvlQtyByItemV1);
    }

    @Test
    public void testGetAvailabilityStockLevelV2() throws Throwable {
        // stubs
        Mockito
                .when(mockAvailabilityService.getStockLevelV2(1L, 1L))
                .thenReturn(expectedStockLevelV2);


        // when
        AvailabilityV2Response actualResponse = availabilityController.getAvailabilityStockLevelV2(1L, 1L);

        // assert
        assertThat(actualResponse).isEqualTo(expectedStockLevelV2);
    }

    @Test
    public void testGetAvailabilityStockLevelV3() throws Throwable {
        // stubs
        Mockito
                .when(mockAvailabilityService.getStockLevelV3(1L, 1L))
                .thenReturn(expectedStockLevelV3);


        // when
        AvailabilityV3Response actualResponse = availabilityController.getAvailabilityStockLevelV3(1L, 1L);

        // assert
        assertThat(actualResponse).isEqualTo(expectedStockLevelV3);
    }
}