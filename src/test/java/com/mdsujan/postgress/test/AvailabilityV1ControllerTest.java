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

    private final Item mockItem = new Item(1L,
            "testDesc",
            "testCategory",
            "testType",
            "testStatus",
            19.99,
            false,
            false,
            false);
    private final Location mockLocation = new Location(
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
    private final Supply mockSupply = new Supply(
            1L,
            AllowedSupplyTypes.ONHAND,
            1L,
            mockItem,
            mockLocation);
    private final Supply mockSupply2 = new Supply(
            1L,
            AllowedSupplyTypes.ONHAND,
            1L,
            mockItem,
            mockLocation);
    private final List<Supply> mockSupplyList = List.of(mockSupply, mockSupply2);
    private final Demand mockDemand = new Demand(
            1L,
            AllowedDemandTypes.HARD_PROMISED,
            1L,
            mockItem,
            mockLocation);
    private final Demand mockDemand2 = new Demand(
            1L,
            AllowedDemandTypes.HARD_PROMISED,
            1L,
            mockItem,
            mockLocation);
    private final List<Demand> mockDemandList = List.of(mockDemand, mockDemand2);

    private final AvailabilityV1Response mockAvailabilityV1Response = new AvailabilityV1Response(
            1L,
            1L,
            4L);

    @Test
    public void testGetAvailabilityQty() throws Throwable {
        // stub
        Mockito
                .when(mockSupplyRepository.findByItemItemIdAndLocationLocationId(mockItem.getItemId(), mockLocation.getLocationId()))
                .thenReturn(mockSupplyList);
        Mockito
                .when(mockDemandRepository.findByItemItemIdAndLocationLocationId(mockItem.getItemId(), mockLocation.getLocationId()))
                .thenReturn(mockDemandList);

        // when
        AvailabilityV1Response availabilityV1Response = mockAvailabilityV1Controller.getAvailabilityQty(mockItem.getItemId(), mockLocation.getLocationId());

        // test
        assertThat(availabilityV1Response).isEqualTo(mockAvailabilityV1Response);
    }

    @Test
    public void testGetAllLocationAvailabilityQty() throws Throwable {
        // stub
        Mockito
                .when(mockSupplyRepository.findByItemItemId(mockItem.getItemId()))
                .thenReturn(mockSupplyList);
        Mockito
                .when(mockDemandRepository.findByItemItemId(mockItem.getItemId()))
                .thenReturn(mockDemandList);

        // when
        AvailabilityV1Response availabilityV1Response = mockAvailabilityV1Controller.getAvailabilityQty(mockItem.getItemId(), mockLocation.getLocationId());

        // test
        assertThat(availabilityV1Response).isEqualTo(mockAvailabilityV1Response);
    }


}
