package com.mdsujan.postgress.test;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.*;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.service.SupplyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;


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
    public void getSupplyByIdTest() throws Throwable {
        // stub
        Mockito
                .when(mockSupplyRepository.findById(1L))
                .thenReturn(Optional.of(mockSupply11));

        // when
        Supply supplyResponse = mockSupplyService.getSupplyById(1L);

        // test
        assertThat(supplyResponse).isEqualTo(mockSupply11);
    }

    @Test
    public void getAllSuppliesTest() throws Throwable {
        // stub
        Mockito
                .when(mockSupplyRepository.findAll())
                .thenReturn(mockSupplyListAll);

        // when
        List<Supply> supplyResponseList = mockSupplyService.getAllSupplies();

        // test
        assertThat(supplyResponseList).isEqualTo(mockSupplyListAll);
    }

    @Test
    public void createSupplyTest() throws Throwable {
        // stubs
        Mockito
                .when(mockLocationRepository.findById(
                        mockCreateSupplyRequest11.getLocationId()
                ))
                .thenReturn(Optional.of(mockLocation1));

        Mockito
                .when(mockItemRepository.findById(
                        mockCreateSupplyRequest11.getItemId()
                ))
                .thenReturn(Optional.of(mockItem1));

        Mockito
                .when(mockSupplyRepository.save(any()))
                .thenReturn(mockSupply11);

        // test
        Supply supplyResponse = mockSupplyService.createNewSupply(mockCreateSupplyRequest11);
        System.out.println("Supply Response: " + supplyResponse);
        // assert
        assertThat(supplyResponse).isEqualTo(mockSupply11);
    }

    @Test
    public void updateSupplyPutTest() throws Throwable {
        Mockito.when(mockSupplyRepository.findById(mockSupplyUpdatePut11.getSupplyId()))
                .thenReturn(Optional.of(mockSupplyUpdatePut11));

        Mockito.when(mockSupplyRepository.save(any())).thenReturn(mockSupplyUpdatePut11);

        Supply supplyResponse = mockSupplyService
                .updateSupplyPut(mockSupplyUpdatePut11.getSupplyId(),
                        mockUpdateSupplyRequest11Put);

//        assertThat(supplyResponse.getSupplyId()).isEqualTo(mockSupplyUpdatePut11.getSupplyId());
        assertThat(supplyResponse).isEqualTo(mockSupplyUpdatePut11);
    }

    @Test
    public void updateSupplyPatchTest() throws Throwable {
        Mockito.when(mockSupplyRepository.findById(mockSupplyUpdatePatch11.getSupplyId()))
                .thenReturn(Optional.of(mockSupplyUpdatePatch11));

        Mockito.when(mockSupplyRepository.save(any())).thenReturn(mockSupplyUpdatePatch11);

        Supply supplyResponse = mockSupplyService
                .updateSupplyPatch(mockSupplyUpdatePatch11.getSupplyId(),
                        mockUpdateSupplyRequest11Patch);

//        assertThat(supplyResponse.getSupplyId()).isEqualTo(mockSupplyUpdatePatch11.getSupplyId());
        assertThat(supplyResponse).isEqualTo(mockSupplyUpdatePatch11);
    }

    @Test
    public void deleteSupplyTest() throws Throwable {
        Mockito
                .when(mockSupplyRepository.findById(mockSupply11.getSupplyId()))
                .thenReturn(Optional.of(mockSupply11));

        String deleteResponse = mockSupplyService.deleteSupply(mockSupply11.getSupplyId());

        assertThat(deleteResponse).isEqualTo("supply with supplyId = '" + mockSupply11.getSupplyId() + "' deleted successfully");
    }
}
