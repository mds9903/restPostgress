package com.mdsujan.postgress.test;

import com.mdsujan.restPostgres.controller.AvailabilityV1Controller;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedDemandTypes;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.AvailabilityV1Response;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvailabilityV1Controller.class)
public class AvailabilityV1ControllerTest {

    @Autowired
    private AvailabilityV1Controller mockAvailabilityV1Controller;
    @MockBean
    private SupplyRepository mockSupplyRepository;

    @MockBean
    private DemandRepository mockDemandRepository;

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
    private final List<Supply> getMockSupplyListItem1LocationAll = List.of( mockSupply11, mockSupply12);

    private final Demand mockDemand11 = new Demand(
            1L,
            AllowedDemandTypes.HARD_PROMISED,
            1L,
            mockItem1,
            mockLocation1);
    private final Demand mockDemand12 = new Demand(
            2L,
            AllowedDemandTypes.HARD_PROMISED,
            1L,
            mockItem1,
            mockLocation2);
    private final List<Demand> mockDemandListItem1Location1 = List.of(mockDemand11);
    // supplies with itemId=2 and locationId=2
    private final List<Demand> getMockDemandListItem1LocationAll = List.of( mockDemand11, mockDemand12);

    private final AvailabilityV1Response mockAvailabilityV1Response = new AvailabilityV1Response(
            1L,
            1L,
            2L);

    private final AvailabilityV1Response mockAvailabilityV1ResponseAllLocations = new AvailabilityV1Response(
            1L,
            "NETWORK",
            3L);

    @Test
    public void testGetAvailabilityQtyByItemAndLocation() throws Throwable {
        // stub
        Mockito
                .when(mockSupplyRepository.findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockSupplyListItem1Location1);
        Mockito
                .when(mockDemandRepository.findByItemItemIdAndLocationLocationId(1L, 1L))
                .thenReturn(mockDemandListItem1Location1);

        // when
        AvailabilityV1Response availabilityV1Response = mockAvailabilityV1Controller.getAvailabilityQtyByItemAndLocation(1L,1L);

        // test
        assertThat(availabilityV1Response).isEqualTo(mockAvailabilityV1Response);
    }

    @Test
    public void testGetAllLocationAvailabilityQty() throws Throwable {
        // stub
        Mockito
                .when(mockSupplyRepository.findByItemItemId(1L))
                .thenReturn(getMockSupplyListItem1LocationAll);
        Mockito
                .when(mockDemandRepository.findByItemItemId(1L))
                .thenReturn(getMockDemandListItem1LocationAll);

        // when
        AvailabilityV1Response availabilityV1Response = mockAvailabilityV1Controller.getAllLocationAvailabilityQty(1l);

        // test
        assertThat(availabilityV1Response).isEqualTo(mockAvailabilityV1ResponseAllLocations);
    }


}
