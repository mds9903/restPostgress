package com.mdsujan.postgress.test;

import com.mdsujan.restPostgres.app.MyApp;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.*;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.service.SupplyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupplyService.class)
public class SupplyServiceTest {

    @Autowired
    private SupplyService mockSupplyService;

    @MockBean
    private SupplyRepository mockSupplyRepository;
    @MockBean
    private ItemRepository mockItemRepository;

    @MockBean
    private LocationRepository mockLocationRepository;

    private final Item mockItem = new Item(
            1L,
            "testDesc",
            "testCategory",
            "testType",
            "testStatus",
            19.99,
            false,
            false,
            false);
    private final Location mockLocation = new Location(
            2L,
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
    private final Supply mockSupply = new Supply(
            1L,
            AllowedSupplyTypes.ONHAND,
            1L,
            mockItem,
            mockLocation);
    private final Supply mockSupply2 = new Supply(
            2L,
            AllowedSupplyTypes.INTRANSIT,
            2L,
            mockItem,
            mockLocation);
    private final List<Supply> mockSupplyList = List.of(mockSupply, mockSupply2);
    private final CreateSupplyRequest mockCreateSupplyRequest = new CreateSupplyRequest(
            mockItem.getItemId(),
            mockLocation.getLocationId(),
            mockSupply.getSupplyType(),
            mockSupply.getSupplyQty()
    );
    private final Supply mockSupplyUpdatePut = new Supply(
            1L,
            AllowedSupplyTypes.INTRANSIT,
            11L,
            mockItem,
            mockLocation);
    private final Supply mockSupplyUpdatePatch = new Supply(
            1L,
            AllowedSupplyTypes.ONHAND,
            22L,
            mockItem,
            mockLocation);

    private final UpdateSupplyRequest mockUpdateSupplyRequest = new UpdateSupplyRequest(
            AllowedSupplyTypes.ONHAND,
            10L,
            1L,
            1L);

    @Test
    public void getSupplyByIdTest() throws Throwable {
        // stub
        Mockito.when(mockSupplyRepository.findById(1L)).thenReturn(Optional.of(mockSupply));

        // when
        Supply supplyResponse = mockSupplyService.getSupplyById(1L);

        // test
        assertThat(supplyResponse).isEqualTo(mockSupply);
    }

    @Test
    public void getAllSuppliesTest() throws Throwable {
        // stub
        Mockito.when(mockSupplyRepository.findAll()).thenReturn(mockSupplyList);

        // when
        List<Supply> supplyResponseList = mockSupplyService.getAllSupplies();

        // test
        assertThat(supplyResponseList).isEqualTo(mockSupplyList);
    }

    @Test
    public void createSupplyTest() throws Throwable {
        // stubs
        Mockito
                .when(mockItemRepository.findById(mockCreateSupplyRequest.getItemId()))
                .thenReturn(Optional.of(mockItem));

        Mockito
                .when(mockLocationRepository.findById(mockCreateSupplyRequest.getLocationId()))
                .thenReturn(Optional.of(mockLocation));

        Mockito
                .when(mockSupplyRepository.save(mockSupply))
                .thenReturn(mockSupply);

        // test
        Supply supplyResponse = mockSupplyService.createNewSupply(mockCreateSupplyRequest);
        System.out.println("Supply Response: "+supplyResponse);
        // assert
        assertThat(supplyResponse).isEqualTo(mockSupply);
    }

    @Test
    public void updateSupplyPutTest() throws Throwable {
        Mockito.when(mockSupplyRepository.findById(mockSupplyUpdatePut.getSupplyId()))
                .thenReturn(Optional.of(mockSupplyUpdatePut));

        Mockito.when(mockSupplyRepository.save(mockSupplyUpdatePut)).thenReturn(mockSupplyUpdatePut);

        Supply supplyResponse = mockSupplyService
                .updateSupplyPut(mockSupplyUpdatePut.getSupplyId(),
                        mockUpdateSupplyRequest);

        assertThat(supplyResponse.getSupplyId()).isEqualTo(mockSupplyUpdatePut.getSupplyId());
    }

    @Test
    public void updateSupplyPatchTest() throws Throwable {
        Mockito.when(mockSupplyRepository.findById(mockSupplyUpdatePatch.getSupplyId()))
                .thenReturn(Optional.of(mockSupplyUpdatePatch));

        Mockito.when(mockSupplyRepository.save(mockSupplyUpdatePatch)).thenReturn(mockSupplyUpdatePatch);

        Supply supplyResponse = mockSupplyService
                .updateSupplyPut(mockSupplyUpdatePatch.getSupplyId(),
                        mockUpdateSupplyRequest);

        assertThat(supplyResponse.getSupplyId()).isEqualTo(mockSupplyUpdatePut.getSupplyId());
    }

    @Test
    public void deleteSupplyTest() throws Throwable {
        Mockito.when(mockSupplyRepository.findById(mockSupply.getSupplyId())).thenReturn(Optional.of(mockSupply));

        String deleteResponse = mockSupplyService.deleteSupply(mockSupply.getSupplyId());

        assertThat(deleteResponse).isEqualTo("supply with supplyId = '" + mockSupply.getSupplyId() + "' deleted successfully");
    }
}
