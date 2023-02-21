package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Demand;
import com.mdsujan.restPostgres.exceptionHandling.DuplicateResourceException;
import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.repository.DemandRepository;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.request.CreateDemandRequest;
import com.mdsujan.restPostgres.request.UpdateDemandRequest;
import com.mdsujan.restPostgres.response.DemandDetailsResponse;
import com.mdsujan.restPostgres.response.DemandResponse;
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
@SpringBootTest(classes = DemandService.class)
public class DemandServiceTest {
    @Autowired
    DemandService demandService;

    @MockBean
    DemandRepository mockDemandRepository;
    @MockBean
    ItemRepository mockItemRepository;
    @MockBean
    LocationRepository mockLocationRepository;

    TestUtils testUtils = new TestUtils();


    @Test
    @DisplayName("should return a list of supplies")
    public void testGetAllDemands() {
        // setup
        List<Demand> expectedDemandList = List.of(testUtils.getTestDemand());

        // stub
        when(mockDemandRepository.findAll()).thenReturn(expectedDemandList);

        // execute
        Object actual = demandService.getAll();

        // assertions
        assertEquals(expectedDemandList, actual);
    }

    @Test
    public void testGetDemandsByItemAndLocation() {
        // setup
        List<Demand> expectedDemandList = List.of(testUtils.getTestDemand());
        Long testItemId = testUtils.getTestItem().getItemId();
        Long testLocationId = testUtils.getTestLocation().getLocationId();
        // stub
        when(mockDemandRepository.findByItemIdAndLocationId(
                anyLong(), anyLong()
        )).thenReturn(expectedDemandList);

        // execute
        Object actual = demandService.getDemandsByItemIdAndLocationId(
                testItemId, testLocationId
        );

        // assertions
        assertEquals(expectedDemandList, actual);
    }

    @Test
    public void testGetDemandsByItemId() throws Throwable {
        // setup
        List<Demand> expectedDemandList = List.of(testUtils.getTestDemand());
        Long testItemId = testUtils.getTestItem().getItemId();
        // stub
        when(mockDemandRepository.findByItemId(anyLong()))
                .thenReturn(expectedDemandList);

        // execute
        Object actual = demandService.getDemandsByItemId(testItemId);

        // assertions
        assertEquals(expectedDemandList, actual);
    }

    @DisplayName("should return a demand by given valid id")
    @Test
    public void testGetDemandById_valid_id() throws Throwable {
        // setup
        Demand expected = testUtils.getTestDemand();

        // stub
        when(mockDemandRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(expected));

        // execute
        Object actual = demandService.getDemandById(expected.getDemandId());

        // assertions
        assertEquals(expected, actual);
    }

    @DisplayName("get ResourceNotFoundException when demand not found")
    @Test
    public void testGetDemandById_invalid_id() throws Throwable {
        // stub
        when(mockDemandRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        // assertions
        assertThrows(ResourceNotFoundException.class, () -> {
            demandService.getDemandById(-999L);
        });
    }

    @DisplayName("get demandResponse when creating demand with valid inputs")
    @Test
    public void testCreateDemand_valid_input() throws Throwable {
        // stub
        CreateDemandRequest testCreateDemandRequest =
                testUtils.getTestCreateDemandRequest();

        Demand expectedDemand = testUtils.getTestDemand();
        // no supplies exist with same demandId
        when(mockDemandRepository.findById(testCreateDemandRequest.getDemandId()))
                .thenReturn(Optional.empty());

        // a demand has to have a location id of a location that exists
        when(mockLocationRepository.findById(testCreateDemandRequest.getLocationId()))
                .thenReturn(Optional.of(testUtils.getTestLocation()));
        // a demand has to have an item id of an item that exists
        when(mockItemRepository.findById(testCreateDemandRequest.getItemId()))
                .thenReturn(Optional.of(testUtils.getTestItem()));

        when(mockDemandRepository.save(any(Demand.class)))
                .thenReturn(expectedDemand);

        // execute
        Object actualDemand = demandService.createNewDemand(testCreateDemandRequest);

        // assertions
        assertEquals(expectedDemand, actualDemand);
    }

    @DisplayName("throw duplicateResourceException when creating duplicate id demand")
    @Test
    public void testCreateDemand_invalid_input() {
        // stub
        when(mockDemandRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(testUtils.getTestDemand()));

        // assertions
        assertThrows(DuplicateResourceException.class, () -> {
            demandService.createNewDemand(
                    testUtils.getTestCreateDemandRequest());
        });
    }

    @DisplayName("batch creation of demand with valid inputs returns a list of demandResponse")
    @Test
    public void testCreateDemandBatch_valid_input() throws Throwable {

        // stub
        List<CreateDemandRequest> createDemandsList =
                List.of(testUtils.getTestCreateDemandRequest());

        List<DemandResponse> expectedDemandResponseList =
                List.of(testUtils.getTestDemandResponse());

        List<Demand> expectedDemandList =
                List.of(testUtils.getTestDemand());

        // stubs
        when(mockDemandRepository.findById(createDemandsList.get(0).getDemandId()))
                .thenReturn(Optional.empty());

        when(mockItemRepository.findById(createDemandsList.get(0).getItemId()))
                .thenReturn(Optional.of(testUtils.getTestItem()));

        when(mockLocationRepository.findById(createDemandsList.get(0).getLocationId()))
                .thenReturn(Optional.of(testUtils.getTestLocation()));

        when(mockDemandRepository.save(any(Demand.class)))
                .thenReturn(expectedDemandList.get(0));

        // execute
        List<DemandResponse> actual = demandService.createNewDemands(createDemandsList);

        // assertions
        assertEquals(expectedDemandResponseList.get(0).getDemandId(),
                actual.get(0).getDemandId());
    }

    @Test
    public void testDeleteDemand_valid_id() throws Throwable {
        // stub
        Demand testDemand = testUtils.getTestDemand();
        when(mockDemandRepository.findById(testDemand.getDemandId()))
                .thenReturn(Optional.of(testDemand));
        doNothing().when(mockDemandRepository).deleteById(anyLong());

        // execute
        Object expected = "".getClass();
        Object actual = demandService.deleteDemand(
                testDemand.getDemandId()).getClass();

        // assert
        assertEquals(expected, actual);

    }


    @Test
    public void testDeleteDemand_invalid_id() {
        // stub
        Demand testDemand = testUtils.getTestDemand();
        when(mockDemandRepository.findById(testDemand.getDemandId()))
                .thenReturn(Optional.empty());

        // assertions
        assertThrows(ResourceNotFoundException.class, () -> {
            demandService.deleteDemand(testDemand.getDemandId());
        });
    }

    @Test
    public void testUpdateDemandPut_valid_inputs() throws Throwable {
        // stub
        UpdateDemandRequest testUpdateDemandRequest = testUtils.getTestUpdateDemandRequestPut();
        Demand testDemand = testUtils.getTestDemand();
        Long testDemandId = testUtils.getTestDemandUpdated().getDemandId();
        when(mockDemandRepository.findById(testDemandId)).thenReturn(Optional.of(testDemand));
        when(mockDemandRepository.save(any(Demand.class))).thenReturn(testDemand);

        // execute
        Demand actual = demandService.updateDemandPut(testDemandId, testUpdateDemandRequest);

        // assertions
        assertEquals(testDemand, actual);
    }

    @Test
    public void testUpdateDemandPatch_valid_inputs() throws Throwable {
        // stub
        UpdateDemandRequest testUpdateDemandRequest = testUtils.getTestUpdateDemandRequestPatch();
        Demand testDemand = testUtils.getTestDemandUpdated();
        Long testDemandId = testDemand.getDemandId();
        when(mockDemandRepository.findById(testDemandId))
                .thenReturn(Optional.of(testDemand));
        when(mockDemandRepository.save(any(Demand.class)))
                .thenReturn(testDemand);

        // execute
        Demand actual = demandService.updateDemandPatch(
                testDemandId, testUpdateDemandRequest);

        // assertions
        assertEquals(testDemand, actual);
    }

    @Test
    public void testGetDemandDetailsByItemAndLocation() throws Throwable {
        // setup
        List<Demand> testDemandList = List.of(testUtils.getTestDemand());
        Long testItemId = testUtils.getTestItem().getItemId();
        Long testLocationId = testUtils.getTestLocation().getLocationId();
        DemandDetailsResponse excpectedhresholdDetailsResponse =
                testUtils.getTestDemandDetailsResponse();
        // stub
        when(mockDemandRepository.findByItemIdAndLocationId(testItemId, testLocationId))
                .thenReturn(testDemandList);
        // execute
        DemandDetailsResponse actual = demandService.getDemandDetailsByItemAndLocation(
                testItemId, testLocationId);
        // assertions
        assertEquals(excpectedhresholdDetailsResponse, actual);
    }

}
