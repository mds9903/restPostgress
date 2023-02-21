package com.mdsujan.restPostgres.service;

import com.mdsujan.restPostgres.TestUtils;
import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.exceptionHandling.DuplicateResourceException;
import com.mdsujan.restPostgres.exceptionHandling.ResourceNotFoundException;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.request.CreateThresholdRequest;
import com.mdsujan.restPostgres.request.UpdateThresholdRequest;
import com.mdsujan.restPostgres.response.ThresholdDetailsResponse;
import com.mdsujan.restPostgres.response.ThresholdResponse;
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
@SpringBootTest(classes = ThresholdService.class)
public class ThresholdServiceTest {
    @Autowired
    ThresholdService thresholdService;

    @MockBean
    ThresholdRepository mockThresholdRepository;
    @MockBean
    ItemRepository mockItemRepository;
    @MockBean
    LocationRepository mockLocationRepository;

    TestUtils testUtils = new TestUtils();


    @Test
    @DisplayName("should return a list of thresholds")
    public void testGetAllThresholds() {
        // setup
        List<Threshold> expectedThresholdList = List.of(testUtils.getTestThreshold());

        // stub
        when(mockThresholdRepository.findAll()).thenReturn(expectedThresholdList);

        // execute
        Object actual = thresholdService.getAllThresholds();

        // assertions
        assertEquals(expectedThresholdList, actual);
    }


    @DisplayName("should return a threshold by given valid id")
    @Test
    public void testGetThresholdById_valid_id() throws Throwable {
        // setup
        Threshold expected = testUtils.getTestThreshold();

        // stub
        when(mockThresholdRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(expected));

        // execute
        Object actual = thresholdService.getThreshold(expected.getThresholdId());

        // assertions
        assertEquals(expected, actual);
    }

    @DisplayName("get ResourceNotFoundException when threshold not found")
    @Test
    public void testGetThresholdById_invalid_id() {
        // stub
        when(mockThresholdRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        // assertions
        assertThrows(ResourceNotFoundException.class, () -> {
            thresholdService.getThreshold(-999L);
        });
    }

    @DisplayName("get thresholdResponse when creating threshold with valid inputs")
    @Test
    public void testCreateThreshold_valid_input() throws Throwable {
        // stub
        CreateThresholdRequest testCreateThresholdRequest =
                testUtils.getTestCreateThresholdRequest();

        Threshold expectedThreshold = testUtils.getTestThreshold();
        // no thresholds exists with same thresholdId
        when(mockThresholdRepository.findById(testCreateThresholdRequest.getThresholdId()))
                .thenReturn(Optional.empty());

        // a threshold has to have a location id of a location that exists
        when(mockLocationRepository.findById(testCreateThresholdRequest.getLocationId()))
                .thenReturn(Optional.of(testUtils.getTestLocation()));
        // a threshold has to have an item id of an item that exists
        when(mockItemRepository.findById(testCreateThresholdRequest.getItemId()))
                .thenReturn(Optional.of(testUtils.getTestItem()));

        when(mockThresholdRepository.save(any(Threshold.class)))
                .thenReturn(expectedThreshold);

        // execute
        Object actualThreshold = thresholdService.createThreshold(testCreateThresholdRequest);

        // assertions
        assertEquals(expectedThreshold, actualThreshold);
    }

    @DisplayName("throw duplicateResourceException when creating duplicate id threshold")
    @Test
    public void testCreateThreshold_invalid_input() {
        // stub
        when(mockThresholdRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(testUtils.getTestThreshold()));

        // assertions
        assertThrows(DuplicateResourceException.class, () -> {
            thresholdService.createThreshold(
                    testUtils.getTestCreateThresholdRequest());
        });
    }

    @DisplayName("batch creation of threshold with valid inputs returns a list of thresholdResponse")
    @Test
    public void testCreateThresholdBatch_valid_input() throws Throwable {

        // stub
        List<CreateThresholdRequest> createThresholdsList =
                List.of(testUtils.getTestCreateThresholdRequest());

        List<ThresholdResponse> expectedThresholdResponseList =
                List.of(testUtils.getTestThresholdResponse());

        List<Threshold> expectedThresholdList =
                List.of(testUtils.getTestThreshold());

        when(mockThresholdRepository.save(any(Threshold.class)))
                .thenReturn(expectedThresholdList.get(0));

        when(mockThresholdRepository.findById(createThresholdsList.get(0).getThresholdId()))
                .thenReturn(Optional.empty());

        when(mockLocationRepository.findById(createThresholdsList.get(0).getLocationId()))
                .thenReturn(Optional.of(testUtils.getTestLocation()));

        when(mockItemRepository.findById(createThresholdsList.get(0).getItemId()))
                .thenReturn(Optional.of(testUtils.getTestItem()));

        // execute
        Object actual = thresholdService.createThresholds(createThresholdsList);

        // assertions
        assertEquals(expectedThresholdResponseList, actual);
    }

    @Test
    public void testDeleteThreshold_valid_id() throws Throwable {
        // stub
        Threshold testThreshold = testUtils.getTestThreshold();
        when(mockThresholdRepository.findById(testThreshold.getThresholdId()))
                .thenReturn(Optional.of(testThreshold));
        doNothing().when(mockThresholdRepository).deleteById(anyLong());

        // execute
        Object expected = "".getClass();
        Object actual = thresholdService.deleteThresholdById(
                testThreshold.getThresholdId()).getClass();

        // assert
        assertEquals(expected, actual);

    }


    @Test
    public void testDeleteThreshold_invalid_id() {
        // stub
        Threshold testThreshold = testUtils.getTestThreshold();
        when(mockThresholdRepository.findById(testThreshold.getThresholdId()))
                .thenReturn(Optional.empty());

        // assertions
        assertThrows(ResourceNotFoundException.class, () -> {
            thresholdService.deleteThresholdById(testThreshold.getThresholdId());
        });
    }

    @Test
    public void testUpdateThresholdPut_valid_inputs() throws Throwable {
        // stub
        UpdateThresholdRequest testUpdateThresholdRequest = testUtils.getTestUpdateThresholdRequestPut();
        Threshold testThreshold = testUtils.getTestThreshold();
        Long testThresholdId = testUtils.getTestUpdatedThreshold().getThresholdId();
        when(mockThresholdRepository.findById(testThresholdId)).thenReturn(Optional.of(testThreshold));
        when(mockThresholdRepository.save(any(Threshold.class))).thenReturn(testThreshold);

        // execute
        Threshold actual = thresholdService.updateThresholdPut(testThresholdId, testUpdateThresholdRequest);

        // assertions
        assertEquals(testThreshold, actual);
    }

    @Test
    public void testUpdateThresholdPatch_valid_inputs() throws Throwable {
        // stub
        UpdateThresholdRequest testUpdateThresholdRequest = testUtils.getTestUpdateThresholdRequestPatch();
        Threshold testThreshold = testUtils.getTestUpdatedThreshold();
        Long testThresholdId = testThreshold.getThresholdId();
        when(mockThresholdRepository.findById(testThresholdId))
                .thenReturn(Optional.of(testThreshold));
        when(mockThresholdRepository.save(any(Threshold.class)))
                .thenReturn(testThreshold);

        // execute
        Threshold actual = thresholdService.updateThresholdPatch(
                testThresholdId, testUpdateThresholdRequest);

        // assertions
        assertEquals(testThreshold, actual);
    }

    @Test
    public void testGetThresholdDetailsByItemAndLocation() {
        // setup
        Threshold testThreshold = testUtils.getTestThreshold();
        Long testItemId = testUtils.getTestItem().getItemId();
        Long testLocationId = testUtils.getTestLocation().getLocationId();
        ThresholdDetailsResponse excpectedhresholdDetailsResponse =
                testUtils.getTestThresholdDetailsResponse();
        // stub
        when(mockThresholdRepository.findByItemIdAndLocationId(testItemId, testLocationId))
                .thenReturn(testThreshold);
        // execute
        ThresholdDetailsResponse actual = thresholdService.getThresholdDetailsByItemAndLocation(
                testItemId, testLocationId);
        // assertions
        assertEquals(excpectedhresholdDetailsResponse, actual);
    }
}
