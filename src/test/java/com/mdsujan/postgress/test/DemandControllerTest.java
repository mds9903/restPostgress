package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.controller.DemandController;
import com.mdsujan.restPostgres.controller.DemandController;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.request.UpdateDemandRequest;
import com.mdsujan.restPostgres.response.DemandResponse;
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
    private final Demand mockDemand11 = new Demand(
            2L,
            AllowedDemandTypes.HARD_PROMISED,
            20L,
            mockItem1,
            mockLocation2);
    private final Demand mockDemand12 = new Demand(
            2L,
            AllowedDemandTypes.HARD_PROMISED,
            20L,
            mockItem1,
            mockLocation2);
    private final List<Demand> mockDemandList = List.of(
            mockDemand11, mockDemand12);

    private final DemandResponse mockDemandResponse11 = new DemandResponse(mockDemand11);

    private final CreateDemandRequest mockCreateDemandRequest = new CreateDemandRequest(
            mockItem1.getItemId(),
            mockLocation1.getLocationId(),
            AllowedDemandTypes.HARD_PROMISED,
            1L);
    private final UpdateDemandRequest mockUpdateDemandRequestPut = new UpdateDemandRequest(
            mockItem1.getItemId(),
            mockLocation1.getLocationId(),
            AllowedDemandTypes.PLANNED,
            (mockDemand11.getDemandQty() + 1L));

    private final Demand mockDemandUpdatedPut = new Demand(
            mockUpdateDemandRequestPut.getItemId(),
            mockUpdateDemandRequestPut.getDemandType(),
            mockUpdateDemandRequestPut.getDemandQty(),
            mockItem1,
            mockLocation1);
    private final DemandResponse mockDemandUpdatedResponsePut = new DemandResponse(mockDemandUpdatedPut);

    private final UpdateDemandRequest mockUpdateDemandRequestPatch = new UpdateDemandRequest(
            mockItem1.getItemId(),
            mockLocation1.getLocationId(),
            AllowedDemandTypes.PLANNED,
            null);

    private final Demand mockDemandUpdatedPatch = new Demand(
            mockUpdateDemandRequestPatch.getItemId(),
            mockUpdateDemandRequestPatch.getDemandType(),
            mockUpdateDemandRequestPatch.getDemandQty(),
            mockItem1,
            mockLocation1);

    private final DemandResponse mockDemandUpdatedResponsePatch = new DemandResponse(mockDemandUpdatedPatch);


    @Test
    public void testGetAllDemands() {
        // stub
        Mockito.when(mockDemandService.getAll()).thenReturn(mockDemandList);

        // when
        List<DemandResponse> demandResponse = demandController.getAllDemands();

        // then
        assertThat(demandResponse.size()).isEqualTo(mockDemandList.size());
    }

    @Test
    public void testGetDemandById() throws Throwable {
        // stub
        Mockito
                .when(mockDemandService.getDemandById(mockDemand11.getDemandId()))
                .thenReturn(mockDemand11);

        // when
        DemandResponse demandResponse = demandController.getDemandById(mockDemand11.getDemandId());

        // then
        assertThat(demandResponse).isEqualTo(mockDemandResponse11);
    }

    @Test
    public void testCreateDemand() throws Throwable {
        // stub
        Mockito
                .when(mockDemandService.createNewDemand(mockCreateDemandRequest))
                .thenReturn(mockDemand11);

        // when
        DemandResponse demandResponse = demandController.createDemand(mockCreateDemandRequest);

        // then
        assertThat(demandResponse).isEqualTo(mockDemandResponse11);
    }

    @Test
    public void testUpdateDemandPut() throws Throwable {
        // stub
        Mockito
                .when(mockDemandService.updateDemandPut(
                        mockDemand11.getDemandId(), mockUpdateDemandRequestPut))
                .thenReturn(mockDemandUpdatedPut);

        // when
        DemandResponse demandResponse = demandController.updateDemandPut(
                mockDemand11.getDemandId(), mockUpdateDemandRequestPut);

        // then
        assertThat(demandResponse).isEqualTo(mockDemandUpdatedResponsePut);
    }

    @Test
    public void testUpdateDemandPatch() throws Throwable {
        // stub
        Mockito
                .when(mockDemandService.updateDemandPatch(
                        mockDemand11.getDemandId(), mockUpdateDemandRequestPatch))
                .thenReturn(mockDemandUpdatedPatch);

        // when
        DemandResponse demandResponse = demandController.updateDemandPatch(
                mockDemand11.getDemandId(), mockUpdateDemandRequestPatch);

        // then
        assertThat(demandResponse).isEqualTo(mockDemandUpdatedResponsePatch);
    }

    @Test
    public void testDeleteDemand() throws Throwable {
        // stub
        Mockito
                .when(mockDemandService.deleteDemand(mockDemand11.getDemandId()))
                .thenReturn("Demand with demandId=" + mockDemand11.getDemandId() + " successfully deleted.");

        // when
        String deleteDemandResponse = demandController.deleteDemand(mockDemand11.getDemandId());

        // then
        assertThat(deleteDemandResponse).isEqualTo("Demand with demandId=" + mockDemand11.getDemandId() + " successfully deleted.");
    }
}
