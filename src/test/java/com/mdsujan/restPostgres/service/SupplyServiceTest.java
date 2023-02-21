package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.exceptionHandling.DuplicateResourceException;
import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.request.CreateSupplyRequest;
import com.mdsujan.restPostgres.request.UpdateSupplyRequest;
import com.mdsujan.restPostgres.response.SupplyDetailsResponse;
import com.mdsujan.restPostgres.response.SupplyResponse;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupplyService.class)
public class SupplyServiceTest {
    @Autowired
    SupplyService supplyService;

    @MockBean
    SupplyRepository mockSupplyRepository;
    @MockBean
    ItemRepository mockItemRepository;
    @MockBean
    LocationRepository mockLocationRepository;

    TestUtils testUtils = new TestUtils();


    @Test
    @DisplayName("should return a list of supplies")
    public void testGetAllSupplies() {
        // setup
        List<Supply> expectedSupplyList = List.of(testUtils.getTestSupply());

        // stub
        when(mockSupplyRepository.findAll()).thenReturn(expectedSupplyList);

        // execute
        Object actual = supplyService.getAllSupplies();

        // assertions
        assertEquals(expectedSupplyList, actual);
    }


    @DisplayName("should return a supply by given valid id")
    @Test
    public void testGetSupplyById_valid_id() throws Throwable {
        // setup
        Supply expected = testUtils.getTestSupply();

        // stub
        when(mockSupplyRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(expected));

        // execute
        Object actual = supplyService.getSupplyById(expected.getSupplyId());

        // assertions
        assertEquals(expected, actual);
    }

    @DisplayName("get ResourceNotFoundException when supply not found")
    @Test
    public void testGetSupplyById_invalid_id() throws Throwable {
        // stub
        when(mockSupplyRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        // assertions
        assertThrows(ResourceNotFoundException.class, () -> {
            supplyService.getSupplyById(-999L);
        });
    }

    @DisplayName("get supplyResponse when creating supply with valid inputs")
    @Test
    public void testCreateSupply_valid_input() throws Throwable {
        // stub
        CreateSupplyRequest testCreateSupplyRequest =
                testUtils.getTestCreateSupplyRequest();

        Supply expectedSupply = testUtils.getTestSupply();
        // no supplies exist with same supplyId
        when(mockSupplyRepository.findById(testCreateSupplyRequest.getSupplyId()))
                .thenReturn(Optional.empty());

        // a supply has to have a location id of a location that exists
        when(mockLocationRepository.findById(testCreateSupplyRequest.getLocationId()))
                .thenReturn(Optional.of(testUtils.getTestLocation()));
        // a supply has to have an item id of an item that exists
        when(mockItemRepository.findById(testCreateSupplyRequest.getItemId()))
                .thenReturn(Optional.of(testUtils.getTestItem()));

        when(mockSupplyRepository.save(any(Supply.class)))
                .thenReturn(expectedSupply);

        // execute
        Object actualSupply = supplyService.createNewSupply(testCreateSupplyRequest);

        // assertions
        assertEquals(expectedSupply, actualSupply);
    }

    @DisplayName("throw duplicateResourceException when creating duplicate id supply")
    @Test
    public void testCreateSupply_invalid_input() {
        // stub
        when(mockSupplyRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(testUtils.getTestSupply()));

        // assertions
        assertThrows(DuplicateResourceException.class, () -> {
            supplyService.createNewSupply(
                    testUtils.getTestCreateSupplyRequest());
        });
    }

    @DisplayName("batch creation of supply with valid inputs returns a list of supplyResponse")
    @Test
    public void testCreateSupplyBatch_valid_input() throws Throwable {

        // stub
        List<CreateSupplyRequest> createSuppliesList =
                List.of(testUtils.getTestCreateSupplyRequest());

        List<SupplyResponse> expectedSupplyResponseList =
                List.of(testUtils.getTestSupplyResponse());

        List<Supply> expectedSupplyList =
                List.of(testUtils.getTestSupply());

        // stubs
        when(mockSupplyRepository.findById(createSuppliesList.get(0).getSupplyId()))
                .thenReturn(Optional.empty());

        when(mockItemRepository.findById(createSuppliesList.get(0).getItemId()))
                .thenReturn(Optional.of(testUtils.getTestItem()));

        when(mockLocationRepository.findById(createSuppliesList.get(0).getLocationId()))
                .thenReturn(Optional.of(testUtils.getTestLocation()));

        when(mockSupplyRepository.save(any(Supply.class)))
                .thenReturn(expectedSupplyList.get(0));

        // execute
        List<SupplyResponse> actual = supplyService.createNewSupplies(createSuppliesList);

        // assertions
        assertEquals(expectedSupplyResponseList.get(0).getSupplyId(),
                actual.get(0).getSupplyId());
    }

    @Test
    public void testDeleteSupply_valid_id() throws Throwable {
        // stub
        Supply testSupply = testUtils.getTestSupply();
        when(mockSupplyRepository.findById(testSupply.getSupplyId()))
                .thenReturn(Optional.of(testSupply));
        doNothing().when(mockSupplyRepository).deleteById(anyLong());

        // execute
        Object expected = "".getClass();
        Object actual = supplyService.deleteSupply(
                testSupply.getSupplyId()).getClass();

        // assert
        assertEquals(expected, actual);

    }


    @Test
    public void testDeleteSupply_invalid_id() {
        // stub
        Supply testSupply = testUtils.getTestSupply();
        when(mockSupplyRepository.findById(testSupply.getSupplyId()))
                .thenReturn(Optional.empty());

        // assertions
        assertThrows(ResourceNotFoundException.class, () -> {
            supplyService.deleteSupply(testSupply.getSupplyId());
        });
    }

    @Test
    public void testUpdateSupplyPut_valid_inputs() throws Throwable {
        // stub
        UpdateSupplyRequest testUpdateSupplyRequest = testUtils.getTestUpdateSupplyRequestPut();
        Supply testSupply = testUtils.getTestSupply();
        Long testSupplyId = testUtils.getTestSupplyUpdated().getSupplyId();
        when(mockSupplyRepository.findById(testSupplyId)).thenReturn(Optional.of(testSupply));
        when(mockSupplyRepository.save(any(Supply.class))).thenReturn(testSupply);

        // execute
        Supply actual = supplyService.updateSupplyPut(testSupplyId, testUpdateSupplyRequest);

        // assertions
        assertEquals(testSupply, actual);
    }

    @Test
    public void testUpdateSupplyPatch_valid_inputs() throws Throwable {
        // stub
        UpdateSupplyRequest testUpdateSupplyRequest = testUtils.getTestUpdateSupplyRequestPatch();
        Supply testSupply = testUtils.getTestSupplyUpdated();
        Long testSupplyId = testSupply.getSupplyId();
        when(mockSupplyRepository.findById(testSupplyId))
                .thenReturn(Optional.of(testSupply));
        when(mockSupplyRepository.save(any(Supply.class)))
                .thenReturn(testSupply);

        // execute
        Supply actual = supplyService.updateSupplyPatch(
                testSupplyId, testUpdateSupplyRequest);

        // assertions
        assertEquals(testSupply, actual);
    }

    @Test
    public void testGetSupplyDetailsByItemAndLocation() throws Throwable {
        // setup
        List<Supply> testSupplyList = List.of(testUtils.getTestSupply());
        Long testItemId = testUtils.getTestItem().getItemId();
        Long testLocationId = testUtils.getTestLocation().getLocationId();
        SupplyDetailsResponse excpectedhresholdDetailsResponse =
                testUtils.getTestSupplyDetailsResponse();
        // stub
        when(mockSupplyRepository.findByItemIdAndLocationId(testItemId, testLocationId))
                .thenReturn(testSupplyList);
        // execute
        SupplyDetailsResponse actual = supplyService.getSupplyDetailsByItemAndLocation(
                testItemId, testLocationId);
        // assertions
        assertEquals(excpectedhresholdDetailsResponse, actual);
    }

}
