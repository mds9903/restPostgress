package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
import com.mdsujan.restPostgres.repository.ItemRepository;
import com.mdsujan.restPostgres.repository.LocationRepository;
import com.mdsujan.restPostgres.repository.SupplyRepository;
import com.mdsujan.restPostgres.repository.ThresholdRepository;
import com.mdsujan.restPostgres.request.CreateThresholdRequest;
import com.mdsujan.restPostgres.request.UpdateThresholdRequest;
import com.mdsujan.restPostgres.response.SupplyResponse;
import com.mdsujan.restPostgres.response.ThresholdDetails;
import com.mdsujan.restPostgres.response.ThresholdDetailsResponse;
import com.mdsujan.restPostgres.response.ThresholdResponse;
import com.mdsujan.restPostgres.service.SupplyService;
import com.mdsujan.restPostgres.service.ThresholdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThresholdService.class)
public class ThresholdServiceTest {

    @Autowired
    private ThresholdService thresholdService;
    @MockBean
    private ThresholdRepository mockThresholdRepository;

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
            99.99,
            false,
            false,
            false);

    private final Location mockLocation = new Location(
            1L,
            "testLocationDesc",
            "testType",
            true,
            true,
            true,
            "addr1","addr2","addr2",
            "testCity",
            "testState",
            "testCountry",
            "testPincode");
    private final Threshold mockThreshold = new Threshold(
            1L,
            mockItem,
            mockLocation,
            1L,
            1L);

    private final List<Threshold> mockThresholdList = List.of(mockThreshold);
    private final ThresholdResponse mockThresholdResponse = new ThresholdResponse(
            1L,
            1L,
            1L,
            1L,
            1L);

    private final List<ThresholdResponse> mockThresholdResponseList = List.of(mockThresholdResponse);

    private final CreateThresholdRequest mockCreateThresholdRequest = new CreateThresholdRequest(
            1L,
            1L,
            1L,
            1L);

    private final UpdateThresholdRequest mockUpdateThresholdRequestPut = new UpdateThresholdRequest(
            1L,
            1L,
            0L,
            0L);

    private final Threshold mockThresholdUpdatePut = new Threshold(
            1L,
            mockItem,
            mockLocation,
            0L,
            0L);
    private final ThresholdResponse mockThresholdResponseUpdatePut = new ThresholdResponse(
            1L,
            1L,
            1L,
            0L,
            0L);

    private final UpdateThresholdRequest mockUpdateThresholdRequestPatch = new UpdateThresholdRequest(
            1L,
            1L,
            2L,
            2L);

    private final Threshold mockThresholdUpdatePatch = new Threshold(
            1L,
            mockItem,
            mockLocation,
            null,
            2L);
    private final ThresholdResponse mockThresholdResponseUpdatePatch = new ThresholdResponse(
            1L,
            1L,
            1L,
            2L,
            2L);

    private final ThresholdDetailsResponse mockThresholdDetails = new ThresholdDetailsResponse(
            1L,
            1L,
            new ThresholdDetails(1L, 1L));

    @Test
    public void testGetAllThresholds() {
        // stubs
        Mockito
                .when(mockThresholdRepository.findAll())
                .thenReturn(mockThresholdList);

        // when
        List<Threshold> thresholdResponseList = thresholdService.getAllThresholds();

        // assert
        assertThat(thresholdResponseList.size()).isEqualTo(mockThresholdResponseList.size());

    }

    @Test
    public void testGetThreshold() throws Throwable {
        // stubs
        Mockito
                .when(mockThresholdRepository.findById(mockThreshold.getThresholdId()))
                .thenReturn(Optional.of(mockThreshold));

        // when
        Threshold thresholdResponse = thresholdService.getThreshold(mockThreshold.getThresholdId());

        // assert
        assertThat(thresholdResponse).isEqualTo(mockThreshold);
    }

    @Test
    public void testGetThresholdDetails() {
        // stubs
        Mockito
                .when(mockThresholdRepository.findByItemItemIdAndLocationLocationId(
                        mockThreshold.getItem().getItemId(),
                        mockThreshold.getLocation().getLocationId()))
                .thenReturn(mockThreshold);

        // when
        ThresholdDetailsResponse thresholdResponse = thresholdService.getThresholdDetailsByItemAndLocation(
                mockThreshold.getItem().getItemId(),
                mockThreshold.getLocation().getLocationId());

        // assert
        assertThat(thresholdResponse).isEqualTo(mockThresholdDetails);
    }

    @Test
    public void testCreateThreshold() throws Throwable {
        // stubs
        Mockito
                .when(mockThresholdRepository.save(any()))
                .thenReturn(mockThreshold);

        Mockito
                .when(mockItemRepository.findById(mockCreateThresholdRequest.getItemId()))
                .thenReturn(Optional.of(mockItem));
        Mockito
                .when(mockLocationRepository.findById(mockCreateThresholdRequest.getLocationId()))
                .thenReturn(Optional.of(mockLocation));

        // when
        Threshold thresholdResponse = thresholdService.createThreshold(mockCreateThresholdRequest);

        // assert

        assertThat(thresholdResponse).isEqualTo(mockThreshold);
    }

    @Test
    public void testUpdateThresholdPut() throws Throwable {
        // stubs
        Mockito
                .when(mockThresholdRepository.findById(mockThreshold.getThresholdId()))
                .thenReturn(Optional.of(mockThreshold));
        Mockito
                .when(mockThresholdRepository.save(any()))
                .thenReturn(mockThresholdUpdatePut);


        // when
        Threshold thresholdResponse = thresholdService.updateThresholdPut(
                mockThreshold.getThresholdId(),
                mockUpdateThresholdRequestPut);

        // assert

        assertThat(thresholdResponse).isEqualTo(mockThresholdUpdatePut);

    }

    @Test
    public void testUpdateThresholdPatch() throws Throwable {
        // stubs
        Mockito
                .when(mockThresholdRepository.findById(mockThreshold.getThresholdId()))
                .thenReturn(Optional.of(mockThreshold));
        Mockito
                .when(mockThresholdRepository.save(any()))
                .thenReturn(mockThresholdUpdatePatch);


        // when
        Threshold thresholdResponse = thresholdService.updateThresholdPatch(
                mockThreshold.getThresholdId(),
                mockUpdateThresholdRequestPatch);

        // assert

        assertThat(thresholdResponse).isEqualTo(mockThresholdUpdatePatch);
    }

    @Test
    public void testDeleteThreshold() throws Throwable {
        // stubs
        Mockito
                .when(mockThresholdRepository.findById(mockThreshold.getThresholdId()))
                .thenReturn(Optional.of(mockThreshold));

        // when
        String thresholdResponse = thresholdService.deleteThresholdById(mockThreshold.getThresholdId());

        // assert
        assertThat(thresholdResponse).isEqualTo("Threshold with thresholdId=" + mockThreshold.getThresholdId() + " successfully deleted.");
    }

}
