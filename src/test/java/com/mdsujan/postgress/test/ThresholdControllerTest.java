package com.mdsujan.postgress.test;


import com.mdsujan.restPostgres.controller.SupplyController;
import com.mdsujan.restPostgres.controller.ThresholdController;
import com.mdsujan.restPostgres.entity.Item;
import com.mdsujan.restPostgres.entity.Location;
import com.mdsujan.restPostgres.entity.Supply;
import com.mdsujan.restPostgres.entity.Threshold;
import com.mdsujan.restPostgres.enums.AllowedSupplyTypes;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThresholdController.class)
public class ThresholdControllerTest {

    @Autowired
    private ThresholdController thresholdController;
    @MockBean
    private ThresholdService mockThresholdService;

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
    private final Threshold mockThreshold =  new Threshold(
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

    private final Threshold mockThresholdUpdatePut =  new Threshold(
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

    private final Threshold mockThresholdUpdatePatch =  new Threshold(
            1L,
            mockItem,
            mockLocation,
            2L,
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
                .when(mockThresholdService.getAllThresholds())
                .thenReturn(mockThresholdList);

        // when
        List<ThresholdResponse> thresholdResponseList = thresholdController.getAllThresholds();

        // assert
        assertThat(thresholdResponseList.size()).isEqualTo(mockThresholdResponseList.size());

    }

    @Test
    public void testGetThreshold() throws Throwable {
        // stubs
        Mockito
                .when(mockThresholdService.getThreshold(1L))
                .thenReturn(mockThreshold);

        // when
        ThresholdResponse thresholdResponse = thresholdController.getThreshold(1L);

        // assert
        assertThat(thresholdResponse).isEqualTo(mockThresholdResponse);
    }

    @Test
    public void testGetThresholdDetails() {
        // stubs
        Mockito
                .when(mockThresholdService.getThresholdDetailsByItemAndLocation(1L, 1L))
                .thenReturn(mockThresholdDetails);

        // when
        ThresholdDetailsResponse thresholdResponse = thresholdController.getThresholdDetails(1L, 1L);

        // assert
        assertThat(thresholdResponse).isEqualTo(mockThresholdDetails);
    }

    @Test
    public void testCreateThreshold() throws Throwable {
        // stubs
        Mockito
                .when(mockThresholdService.createThreshold(mockCreateThresholdRequest))
                .thenReturn(mockThreshold);

        // when
        ThresholdResponse thresholdResponse = thresholdController.createThreshold(mockCreateThresholdRequest);

        // assert
        assertThat(thresholdResponse.getMaxThreshold()).isEqualTo(mockThresholdResponse.getMaxThreshold());
        assertThat(thresholdResponse.getMinThreshold()).isEqualTo(mockThresholdResponse.getMinThreshold());
        assertThat(thresholdResponse.getItemId()).isEqualTo(mockThresholdResponse.getThresholdId());
        assertThat(thresholdResponse.getLocationId()).isEqualTo(mockThresholdResponse.getLocationId());
    }

    @Test
    public void testUpdateThresholdPut() throws Throwable {
        // stubs
        Mockito
                .when(mockThresholdService.updateThresholdPut(1L, mockUpdateThresholdRequestPut))
                .thenReturn(mockThresholdUpdatePut);

        // when
        ThresholdResponse thresholdResponse = thresholdController.updateThresholdPut(1L, mockUpdateThresholdRequestPut);

        // assert
        assertThat(thresholdResponse.getItemId())
                .isEqualTo(mockThresholdResponseUpdatePut.getItemId());
        assertThat(thresholdResponse.getLocationId())
                .isEqualTo(mockThresholdResponseUpdatePut.getLocationId());
        assertThat(thresholdResponse.getMinThreshold())
                .isEqualTo(mockThresholdResponseUpdatePut.getMinThreshold());
        assertThat(thresholdResponse.getMaxThreshold())
                .isEqualTo(mockThresholdResponseUpdatePut.getMaxThreshold());

    }

    @Test
    public void testUpdateThresholdPatch() throws Throwable {
// stubs
        Mockito
                .when(mockThresholdService.updateThresholdPatch(1L, mockUpdateThresholdRequestPatch))
                .thenReturn(mockThresholdUpdatePatch);

        // when
        ThresholdResponse thresholdResponse = thresholdController.updateThresholdPatch(1L, mockUpdateThresholdRequestPatch);

        // assert
        assertThat(thresholdResponse.getItemId())
                .isEqualTo(mockThresholdResponseUpdatePatch.getItemId());
        assertThat(thresholdResponse.getLocationId())
                .isEqualTo(mockThresholdResponseUpdatePatch.getLocationId());
        assertThat(thresholdResponse.getMinThreshold())
                .isEqualTo(mockThresholdResponseUpdatePatch.getMinThreshold());
        assertThat(thresholdResponse.getMaxThreshold())
                .isEqualTo(mockThresholdResponseUpdatePatch.getMaxThreshold());
    }

    @Test
    public void testDeleteThreshold() throws Throwable {
        // stubs
        Mockito
                .when(mockThresholdService.deleteThresholdById(1L))
                .thenReturn("Threshold with thresholdId=" + mockThreshold.getThresholdId() + " successfully deleted.");

        // when
        String thresholdResponse = thresholdController.deleteThreshold(1L);

        // assert
        assertThat(thresholdResponse).isEqualTo("Threshold with thresholdId=" + mockThreshold.getThresholdId() + " successfully deleted.");
    }

}
