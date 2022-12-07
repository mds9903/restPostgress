package com.mdsujan.postgress.test;

import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.repository.*;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.request.UpdateDemandRequest;
import com.mdsujan.restPostgres.service.DemandService;
import com.mdsujan.restPostgres.service.DemandService;
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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemandService.class)
public class DemandServiceTest {

    @Autowired
    private DemandService mockDemandService;

    @MockBean
    private DemandRepository mockDemandRepository;
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
    private final List<Demand> mockDemandListAll = List.of(
            mockDemand11,
            mockDemand12);

    private final List<Demand> getMockDemandListItem1LocationAll = List.of(mockDemand11, mockDemand12);
    private final CreateDemandRequest mockCreateDemandRequest11 = new CreateDemandRequest(
            mockItem1.getItemId(),
            mockLocation1.getLocationId(),
            mockDemand11.getDemandType(),
            mockDemand11.getDemandQty()
    );
    private final Demand mockDemandUpdatePut11 = new Demand(
            mockDemand11.getDemandId(),
            AllowedDemandTypes.HARD_PROMISED,
            1L,
            mockItem1,
            mockLocation1);
    private final Demand mockDemandUpdatePatch11 = new Demand(
            mockDemand11.getDemandId(),
            AllowedDemandTypes.HARD_PROMISED,
            null,
            mockItem1,
            mockLocation1);

    private final UpdateDemandRequest mockUpdateDemandRequest11Put = new UpdateDemandRequest(
            1L,
            1L,
            1L,
            AllowedDemandTypes.HARD_PROMISED);

    private final UpdateDemandRequest mockUpdateDemandRequest11Patch = new UpdateDemandRequest(
            1L,
            1L,
            null,
            AllowedDemandTypes.HARD_PROMISED);

    @Test
    public void getDemandByIdTest() throws Throwable {
        // stub
        Mockito
                .when(mockDemandRepository.findById(1L))
                .thenReturn(Optional.of(mockDemand11));

        // when
        Demand supplyResponse = mockDemandService.getDemandById(1L);

        // test
        assertThat(supplyResponse).isEqualTo(mockDemand11);
    }

    @Test
    public void getAllSuppliesTest() throws Throwable {
        // stub
        Mockito
                .when(mockDemandRepository.findAll())
                .thenReturn(mockDemandListAll);

        // when
        List<Demand> supplyResponseList = mockDemandService.getAll();

        // test
        assertThat(supplyResponseList).isEqualTo(mockDemandListAll);
    }

    @Test
    public void createDemandTest() throws Throwable {
        // stubs
        Mockito
                .when(mockLocationRepository.findById(
                        mockCreateDemandRequest11.getLocationId()
                ))
                .thenReturn(Optional.of(mockLocation1));

        Mockito
                .when(mockItemRepository.findById(
                        mockCreateDemandRequest11.getItemId()
                ))
                .thenReturn(Optional.of(mockItem1));

        Mockito
                .when(mockDemandRepository.save(mockDemand11))
                .thenReturn(mockDemand11);

        // test
        Demand supplyResponse = mockDemandService.createNewDemand(mockCreateDemandRequest11);
        System.out.println("Demand Response: " + supplyResponse);
        // assert
        assertThat(supplyResponse).isEqualTo(mockDemand11);
    }

//    @Test
//    public void updateDemandPutTest() throws Throwable {
//        Mockito.when(mockDemandRepository.findById(mockDemandUpdatePut.getDemandId()))
//                .thenReturn(Optional.of(mockDemandUpdatePut));
//
//        Mockito.when(mockDemandRepository.save(mockDemandUpdatePut)).thenReturn(mockDemandUpdatePut);
//
//        Demand supplyResponse = mockDemandService
//                .updateDemandPut(mockDemandUpdatePut.getDemandId(),
//                        mockUpdateDemandRequest);
//
//        assertThat(supplyResponse.getDemandId()).isEqualTo(mockDemandUpdatePut.getDemandId());
//    }

//    @Test
//    public void updateDemandPatchTest() throws Throwable {
//        Mockito.when(mockDemandRepository.findById(mockDemandUpdatePatch.getDemandId()))
//                .thenReturn(Optional.of(mockDemandUpdatePatch));
//
//        Mockito.when(mockDemandRepository.save(mockDemandUpdatePatch)).thenReturn(mockDemandUpdatePatch);
//
//        Demand supplyResponse = mockDemandService
//                .updateDemandPut(mockDemandUpdatePatch.getDemandId(),
//                        mockUpdateDemandRequest);
//
//        assertThat(supplyResponse.getDemandId()).isEqualTo(mockDemandUpdatePut.getDemandId());
//    }

    @Test
    public void deleteDemandTest() throws Throwable {
        Mockito
                .when(mockDemandRepository.findById(mockDemand11.getDemandId()))
                .thenReturn(Optional.of(mockDemand11));

        String deleteResponse = mockDemandService.deleteDemand(mockDemand11.getDemandId());

        assertThat(deleteResponse).isEqualTo("demand with demandId = '" + mockDemand11.getDemandId() + "' deleted successfully");
    }
}
