package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.controller.ItemController;
import com.mdsujan.restPostgres.controller.SupplyController;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.service.ItemService;
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
@SpringBootTest(classes = SupplyController.class)
public class SupplyControllerTest {

    @Autowired
    private SupplyController supplyController;

    @MockBean
    private SupplyService mockSupplyService;

    // a single supplyResponse for testing
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
    private final List<Supply> mockSupplyListAll = List.of(
            mockSupply11,
            mockSupply12);

    private final List<Supply> getMockSupplyListItem1LocationAll = List.of(mockSupply11, mockSupply12);

    private final SupplyResponse mockSupplyResponse11 = new SupplyResponse(mockSupply11);

    private final CreateSupplyRequest mockCreateSupplyRequest11 = new CreateSupplyRequest(
            mockItem1.getItemId(),
            mockLocation1.getLocationId(),
            mockSupply11.getSupplyType(),
            mockSupply11.getSupplyQty()
    );
    private final Supply mockSupplyUpdatePut11 = new Supply(
            mockSupply11.getSupplyId(),
            AllowedSupplyTypes.INTRANSIT,
            1L,
            mockItem1,
            mockLocation1);
    private final Supply mockSupplyUpdatePatch11 = new Supply(
            mockSupply11.getSupplyId(),
            AllowedSupplyTypes.ONHAND,
            null,
            mockItem1,
            mockLocation1);

    private final UpdateSupplyRequest mockUpdateSupplyRequest11Put = new UpdateSupplyRequest(
            AllowedSupplyTypes.ONHAND,
            10L,
            1L,
            1L);

    private final UpdateSupplyRequest mockUpdateSupplyRequest11Patch = new UpdateSupplyRequest(
            AllowedSupplyTypes.ONHAND,
            10L,
            1L,
            1L);

    @Test
    public void testGetAllSupplies() {
        // stub
        Mockito.when(mockSupplyService.getAllSupplies()).thenReturn(mockSupplyListAll);

        // when
        List<SupplyResponse> supplyResponse = supplyController.getAllSupplies();

        // then
        assertThat(supplyResponse.size()).isEqualTo(mockSupplyListAll.size());
    }

    @Test
    public void testGetSupplyById() throws Throwable {
        // stub
        Mockito.when(mockSupplyService.getSupplyById(mockSupply11.getSupplyId())).thenReturn(mockSupply11);

        // when
        SupplyResponse supplyResponse = supplyController.getSupplyById(mockSupply11.getSupplyId());

        // then
//        assertThat(supplyResponse).isEqualTo(mockSupplyResponse);

        assertThat(supplyResponse.getSupplyId()).isEqualTo(mockSupplyResponse11.getSupplyId());
        assertThat(supplyResponse.getType()).isEqualTo(mockSupplyResponse11.getType());
        assertThat(supplyResponse.getSupplyQty()).isEqualTo(mockSupplyResponse11.getSupplyQty());
        assertThat(supplyResponse.getItemId()).isEqualTo(mockSupplyResponse11.getItemId());
        assertThat(supplyResponse.getLocationId()).isEqualTo(mockSupplyResponse11.getLocationId());
    }

    @Test
    public void testCreateSupply() throws Throwable {
        // stub
        Mockito.when(mockSupplyService.createNewSupply(mockCreateSupplyRequest11)).thenReturn(mockSupply11);

        // when
        SupplyResponse supplyResponse = supplyController.createSupply(mockCreateSupplyRequest11);

        // then
        assertThat(supplyResponse.getSupplyId()).isEqualTo(mockSupplyResponse11.getSupplyId());
    }

    @Test
    public void testUpdateSupplyPut() throws Throwable {
        // stub
        Mockito
                .when(mockSupplyService.updateSupplyPut(
                        mockSupply11.getSupplyId(), mockUpdateSupplyRequest11Put))
                .thenReturn(mockSupplyUpdatePut11);

        // when
        SupplyResponse supplyResponse = supplyController.updateSupplyPut(
                mockSupply11.getSupplyId(),
                mockUpdateSupplyRequest11Put);

        // then
        assertThat(supplyResponse.getSupplyId()).isEqualTo(mockSupplyUpdatePut11.getSupplyId());
    }


    @Test
    public void testUpdateSupplyPatch() throws Throwable {
        // stub
        Mockito
                .when(mockSupplyService.updateSupplyPatch(
                        mockSupply11.getSupplyId(), mockUpdateSupplyRequest11Patch))
                .thenReturn(mockSupplyUpdatePatch11);

        // when
        SupplyResponse supplyResponse = supplyController.updateSupplyPatch(
                mockSupply11.getSupplyId(),
                mockUpdateSupplyRequest11Patch);

        // then
        assertThat(supplyResponse.getSupplyId()).isEqualTo(mockSupplyUpdatePatch11.getSupplyId());
    }

    @Test
    public void testDeleteSupply() throws Throwable {
        // stub
        Mockito.when(mockSupplyService.deleteSupply(mockSupply11.getSupplyId()))
                .thenReturn("Supply with supplyId=" + mockSupply11.getSupplyId() + " successfully deleted.");

        // when
        String deleteSupplyResponse = supplyController.deleteSupply(mockSupply11.getSupplyId());

        // then
        assertThat(deleteSupplyResponse).isEqualTo("Supply with supplyId=" + mockSupply11.getSupplyId() + " successfully deleted.");
    }


}
