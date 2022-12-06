package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.controller.SupplyController;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.response.SupplyResponse;
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
public class V3AvailabilityControllerTest {

    @Autowired
    private SupplyController supplyController;

    @MockBean
    private SupplyService mockSupplyService;

//    // a single item (dependency) for testing supply
//    private final Item mockItem = );

//    // a single location (dependency) for testing supply
//    private final Location mockLocation = ;

    // a single supplyResponse for testing
    private final Supply mockSupply = new Supply(1L, AllowedSupplyTypes.ONHAND, 10L, new Item(1L, "testDesc", "testCategory", "testType", "testStatus", 11.99, false, false, false), new Location(1L, "testLocationDesc", "testType", true, true, true, "testCity", "testState", "testCountry", "testPincode"));

    private final Supply mockSupply2 = new Supply(2L, AllowedSupplyTypes.ONHAND, 10L, new Item(2L, "testDesc2", "testCategory2", "testType2", "testStatus2", 22.99, false, false, false), new Location(2L, "testLocationDesc2", "testType2", true, true, true, "testCity2", "testState2", "testCountry2", "testPincode2"));


    private final List<Supply> mockSupplyList = List.of(mockSupply, mockSupply2);


    @Test
    public void testGetAllSupplies() {
        // stub
        Mockito.when(mockSupplyService.getAllSupplies()).thenReturn(mockSupplyList);

        // when
        List<SupplyResponse> supplyResponse = supplyController.getAllSupplies();

        // then
        assertThat(supplyResponse.size()).isEqualTo(mockSupplyList.size());
    }

    @Test
    public void testGetSupplyById() throws Throwable {
        // stub
        Mockito.when(mockSupplyService.getSupplyById(mockSupply.getSupplyId())).thenReturn(mockSupply);

        SupplyResponse mockSupplyResponse = new SupplyResponse(mockSupply);
        // when
        SupplyResponse supplyResponse = supplyController.getSupplyById(mockSupply.getSupplyId());

        // then
//        assertThat(supplyResponse).isEqualTo(mockSupplyResponse);

        assertThat(supplyResponse.getSupplyId()).isEqualTo(mockSupplyResponse.getSupplyId());
        assertThat(supplyResponse.getType()).isEqualTo(mockSupplyResponse.getType());
        assertThat(supplyResponse.getSupplyQty()).isEqualTo(mockSupplyResponse.getSupplyQty());
        assertThat(supplyResponse.getItemId()).isEqualTo(mockSupplyResponse.getItemId());
        assertThat(supplyResponse.getLocationId()).isEqualTo(mockSupplyResponse.getLocationId());
    }
}
