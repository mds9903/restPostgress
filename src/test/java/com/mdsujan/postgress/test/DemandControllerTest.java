package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.controller.DemandController;
import com.mdsujan.restPostgres.controller.SupplyController;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.response.DemandResponse;
import com.mdsujan.restPostgres.service.DemandService;
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
@SpringBootTest(classes = DemandController.class)
public class DemandControllerTest {

    @Autowired
    private DemandController demandController;

    @MockBean
    private DemandService mockDemandService;

    private final Item mockItem = new Item(1L, "testDesc", "testCategory", "testType", "testStatus", 99.99, false, false, false);
    private final Location mockLocation = new Location(1L, "testLocationDesc", "testType", true, true, true,"addr1","addr2","addr2", "testCity", "testState", "testCountry", "testPincode");
    private final Demand mockDemand = new Demand(1L, AllowedDemandTypes.PLANNED, 10L, mockItem, mockLocation);
    private final Demand mockDemand2 = new Demand(2L, AllowedDemandTypes.HARD_PROMISED, 20L, mockItem, mockLocation);
    private final List<Demand> mockDemandList = List.of(mockDemand, mockDemand2);

    @Test
    public void testGetAllDemands() {
        // stub
        Mockito
                .when(mockDemandService.getAll())
                .thenReturn(mockDemandList);

        // when
        List<DemandResponse> demandResponses = demandController.getAllDemands();

        // then
        assertThat(demandResponses.size()).isEqualTo(mockDemandList.size());
    }

    @Test
    public void testGetSupplyById() throws Throwable {
        // stub
        Mockito.when(mockDemandService.getDemandById(mockDemand.getDemandId())).thenReturn(mockDemand);

        // to compare the actual response with the expected demand response
        // comparison of the fields not the object equality because new object is returned
        DemandResponse mockDemandResponse = new DemandResponse(mockDemand);

        // test
        DemandResponse demandResponse = demandController.getDemandById(mockDemand.getDemandId());

        // assert
        assertThat(demandResponse.getDemandId()).isEqualTo(mockDemandResponse.getDemandId());
        assertThat(demandResponse.getDemandType()).isEqualTo(mockDemandResponse.getDemandType());
        assertThat(demandResponse.getDemandQty()).isEqualTo(mockDemandResponse.getDemandQty());
        assertThat(demandResponse.getItemId()).isEqualTo(mockDemandResponse.getItemId());
        assertThat(demandResponse.getLocationId()).isEqualTo(mockDemandResponse.getLocationId());
    }
}
